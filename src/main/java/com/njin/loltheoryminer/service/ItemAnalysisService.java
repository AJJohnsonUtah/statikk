/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.service;

import com.njin.loltheory.entity.FinalBuildOrder;
import com.njin.loltheory.riotapi.model.Event;
import com.njin.loltheory.riotapi.model.EventType;
import com.njin.loltheory.riotapi.model.Frame;
import com.njin.loltheory.riotapi.model.ItemDto;
import com.njin.loltheory.riotapi.model.ItemListDto;
import com.njin.loltheory.riotapi.model.MatchDetail;
import com.njin.loltheory.riotapi.model.ParticipantIdentity;
import com.njin.loltheory.riotapi.model.Region;
import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheory.service.FinalBuildOrderService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class ItemAnalysisService {
    
    @Autowired
    RiotApiService riotApiService;
    
    @Autowired
    FinalBuildOrderService finalBuildOrderService;
    
    private ItemListDto itemListDto;
    
    private Set<Integer> finalItemIds;

    /**
     * Populates the item data within the instance of ItemAnalysisService.
     *
     * Final items are populated, along with the itemListDto from the Riot API
     */
    public void loadItems() {
        finalItemIds = new HashSet<>();
        itemListDto = riotApiService.getItemsData(Region.NA);
        for (ItemDto item : itemListDto.getData().values()) {
            if (item.isInStore() && !hasPurchasableParents(item) && (item.getGold().getTotal() >= 1000 || (item.getFrom() != null && !item.getFrom().isEmpty() && !item.getPlaintext().contains("Transforms into a ")))) {
                finalItemIds.add(item.getId());
                for (Integer parentId : item.getInto()) {
                    if (itemListDto.getData().containsKey(parentId)) {
                        finalItemIds.add(parentId);
                    }
                }
                
            }
        }
        
    }

    /**
     * Returns true if this item can be upgraded into another item in the store.
     *
     * @param itemDto
     * @return
     */
    private boolean hasPurchasableParents(ItemDto itemDto) {
        if (itemDto.getInto() == null) {
            return false;
        }
        for (Integer parentId : itemDto.getInto()) {
            if (itemListDto.getData().containsKey(parentId)) {
                if (itemListDto.getData().get(parentId).isInStore()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if this item would be considered a complete item in a
     * player's build. Typically final items cannot be built into anything else,
     * and are the more expensive items in the shop.
     *
     * @param itemId
     * @return
     */
    public boolean isFinalItem(Integer itemId) {
        return finalItemIds.contains(itemId);
    }

    /**
     * Returns the id of the most basic final item that this item comes from.
     *
     * e.g., Murumana would return Manamune's id, and Infinity Edge would stay
     * the same
     *
     * @param itemId - the item id to reduce to a basic final item
     * @return - the id of the reduced final item
     */
    public Integer toBaseFinalItem(Integer itemId) {
        if (itemListDto.getData().containsKey(itemId)) {
            List<Integer> fromItems = itemListDto.getData().get(itemId).getFrom();
            if (fromItems != null && !fromItems.isEmpty()) {
                if (isFinalItem(fromItems.get(0))) {
                    return fromItems.get(0);
                }
            }
        }
        return itemId;
    }
    
    public void loadFinalBuildOrders(MatchDetail match) {
        
        HashMap<Integer, LinkedList<Event>> buildItemIdStacks = new HashMap<>();
        
        for (ParticipantIdentity partId : match.getParticipantIdentities()) {
            buildItemIdStacks.put(partId.getParticipantId(), new LinkedList<>());
        }
        
        for (Frame frame : match.getTimeline().getFrames()) {
            if (frame.getEvents() == null) {
                continue;
            }
            for (Event event : frame.getEvents()) {
                if (event.getEventType() != null && event.getEventType().isItemEvent()) {
                    if (!buildItemIdStacks.containsKey(event.getParticipantId())) {
                        continue;
                    }
                    LinkedList<Event> participantItems = buildItemIdStacks.get(event.getParticipantId());
                    switch (event.getEventType()) {
                        case ITEM_PURCHASED:
                        case ITEM_DESTROYED:
                        case ITEM_SOLD:
                            participantItems.push(event);
                            break;
                        case ITEM_UNDO:
                            participantItems.removeLast();
                            break;
                    }
                }
            }
        }
        
        for (Entry<Integer, LinkedList<Event>> entry : buildItemIdStacks.entrySet()) {
            String buildOrder = "";
            for (Event event : entry.getValue()) {
                if (event.getEventType() == EventType.ITEM_PURCHASED && isFinalItem(event.getItemId())) {
                    buildOrder += (buildOrder.length() == 0 ? event.getItemId() : ("," + event.getItemId()));
                }
            }
            FinalBuildOrder build = new FinalBuildOrder(buildOrder);
            build = finalBuildOrderService.loadEntity(build);
            match.getParticipantFromId(entry.getKey()).setFinalBuildOrder(build);
        }
    }
}
