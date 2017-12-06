/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.riotapi.model.ItemStat;
import statikk.domain.entity.FinalBuildOrder;
import statikk.domain.entity.enums.Role;
import statikk.domain.riotapi.model.Event;
import statikk.domain.riotapi.model.EventType;
import static statikk.domain.riotapi.model.EventType.ITEM_DESTROYED;
import static statikk.domain.riotapi.model.EventType.ITEM_PURCHASED;
import static statikk.domain.riotapi.model.EventType.ITEM_SOLD;
import static statikk.domain.riotapi.model.EventType.ITEM_UNDO;
import statikk.domain.riotapi.model.Frame;
import statikk.domain.riotapi.model.ItemDto;
import statikk.domain.riotapi.model.ItemListDto;
import statikk.domain.riotapi.model.MapType;
import statikk.domain.riotapi.model.MatchDetail;
import statikk.domain.riotapi.model.ParticipantDto;
import statikk.domain.riotapi.model.ParticipantIdentityDto;
import statikk.domain.riotapi.model.Region;
import statikk.domain.riotapi.service.RiotApiService;
import statikk.domain.service.FinalBuildOrderService;

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

    private Map<ItemStat, Double> averageStatCosts;

    public ItemAnalysisService(RiotApiService riotApiService, FinalBuildOrderService finalBuildOrderService) {
        this.riotApiService = riotApiService;
        this.finalBuildOrderService = finalBuildOrderService;
    }

    /**
     * Populates the item data within the instance of ItemAnalysisService.
     *
     * Final items are populated, along with the itemListDto from the Riot API
     */
    public void loadItems() {
        if (finalItemIds != null && itemListDto != null) {
            return;
        }
        finalItemIds = new HashSet<>();
        itemListDto = riotApiService.getItemsData(Region.NA);
        for (ItemDto item : itemListDto.getData().values()) {
            item.syncItemStatsFromDescription();
            if (item.isInStore()
                    && !hasPurchasableParents(item)
                    && (item.getGold().getTotal() >= 1000
                    || ((item.getGold().getTotal() == 0 || item.getTags().contains("Boots"))
                    && item.getFrom() != null && !item.getFrom().isEmpty()
                    && !item.getPlaintext().contains("Transforms into a ")))) {
                finalItemIds.add(item.getId());
                for (Integer parentId : item.getInto()) {
                    if (itemListDto.getData().containsKey(parentId)) {
                        finalItemIds.add(parentId);
                    }
                }
                if (item.getColloq() != null && item.getColloq().contains("Ornn") && item.getFrom() != null && !item.getFrom().isEmpty()) {
                    item.getFrom().forEach((childItem) -> finalItemIds.add(childItem));
                }
            }
        }
        correctStaticItemDataIssues();
        this.averageStatCosts = this.generateAverageCostPerGold();
    }

    private void correctStaticItemDataIssues() {

        // Muramana and Seraph's Embrace (and quick charge variants) are final
        this.finalItemIds.add(3040);
        this.finalItemIds.add(3042);
        this.finalItemIds.add(3043);
        this.finalItemIds.add(3048);

        this.itemListDto.getData().get(3003).addIntoItem(3040); // Archangel's -> Seraph's
        this.itemListDto.getData().get(3007).addIntoItem(3048); // Archangel's quick -> Seraph's
        this.itemListDto.getData().get(3004).addIntoItem(3042); // Manamune's -> Muramana's
        this.itemListDto.getData().get(3008).addIntoItem(3043); // Manamune's quick -> Muramana's
        
        this.itemListDto.getData().get(3040).addFromItem(3003); // Archangel's <- Seraph's
        this.itemListDto.getData().get(3048).addFromItem(3007); // Archangel's quick <- Seraph's
        this.itemListDto.getData().get(3042).addFromItem(3004); // Manamune's <- Muramana's
        this.itemListDto.getData().get(3043).addFromItem(3008); // Manamune's quick <- Muramana's
        
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

    public void loadParticipantRoles(MatchDetail match) {
        for (ParticipantDto participant : match.getParticipants()) {
            Role role = calculateRoleFromBuild(participant.getFinalBuildOrder().getBuildItemIds());
            System.out.println(" " + role);
            participant.setRole(role);
        }
    }

    public void loadFinalBuildOrders(MatchDetail match) {

        HashMap<Integer, LinkedList<Event>> buildItemIdStacks = new HashMap<>();

        for (ParticipantIdentityDto partId : match.getParticipantIdentities()) {
            buildItemIdStacks.put(partId.getParticipantId(), new LinkedList<>());
        }
        if (match.getTimeline() == null || match.getTimeline().getFrames() == null) {
            return;
        }
        for (Frame frame : match.getTimeline().getFrames()) {
            if (frame.getEvents() == null) {
                continue;
            }
            for (Event event : frame.getEvents()) {
                if (event.getType() != null && event.getType().isItemEvent()) {
                    if (!buildItemIdStacks.containsKey(event.getParticipantId())) {
                        continue;
                    }
                    LinkedList<Event> participantItems = buildItemIdStacks.get(event.getParticipantId());
                    switch (event.getType()) {
                        case ITEM_PURCHASED:
                        case ITEM_DESTROYED:
                        case ITEM_SOLD:
                            participantItems.addLast(event);
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
                if (event.getType() == EventType.ITEM_PURCHASED && isFinalItem(event.getItemId())) {
                    buildOrder += (buildOrder.length() == 0 ? event.getItemId() : ("," + event.getItemId()));
                }
            }
            FinalBuildOrder build = new FinalBuildOrder(buildOrder);
            build = finalBuildOrderService.findOrCreate(build);
            match.getParticipantFromId(entry.getKey()).setFinalBuildOrder(build);
        }
    }

    public HashMap<ItemStat, Double> generateAverageCostPerGold() {
        HashSet<ItemStat> statsToAnalyze = ItemStat.valuesHashSet();
        HashMap<ItemStat, Double> averageCosts = new HashMap<>();

        while (!statsToAnalyze.isEmpty()) {
            ItemStat analyzedStat = null;
            for (ItemStat stat : statsToAnalyze) {
                Set<ItemDto> itemsToAnalyzeForStat = getAnalyzableItemsForStat(stat, statsToAnalyze);

                if (!itemsToAnalyzeForStat.isEmpty()) {
                    double costSum = 0;
                    double statSum = 0;
                    for (ItemDto item : itemsToAnalyzeForStat) {
                        double adjustedCost = item.getGold().getTotal();
                        for (Entry<ItemStat, Double> statOnItem : item.getStats().entrySet()) {
                            if (statOnItem.getKey() != stat) {
                                adjustedCost -= averageCosts.get(statOnItem.getKey()) * statOnItem.getValue();
                            }
                        }
                        // If these stats aren't TOO overkil...?
                        if (adjustedCost > 0) {
                            costSum += adjustedCost;
                            statSum += item.getStats().get(stat);
                        }
                    }
                    averageCosts.put(stat, costSum / statSum);
                    analyzedStat = stat;
                    break;
                }
            }
            if (analyzedStat != null) {
                statsToAnalyze.remove(analyzedStat);
            } else {
                // If we tried to analyze stats, just set the rest equal to the average 
                double averageTotalCostPerStat = 0;
                for (Double statAvgCost : averageCosts.values()) {
                    averageTotalCostPerStat += statAvgCost;
                }
                averageTotalCostPerStat /= averageCosts.size();
                for (ItemStat stat : statsToAnalyze) {
                    averageCosts.put(stat, averageTotalCostPerStat);
                }
                statsToAnalyze.clear();
            }

        }

        return averageCosts;
    }

    private Set<ItemDto> getAnalyzableItemsForStat(ItemStat stat, HashSet<ItemStat> statsToAnalyze) {
        HashSet<ItemDto> analyzableItems = new HashSet<>();
        List<ItemDto> itemsWithStat = this.itemListDto.getData().values().stream().filter(item -> item.getStats().containsKey(stat)).collect(Collectors.toList());
        for (ItemDto item : itemsWithStat) {
            // If item has NO kids and only 1 stat, it's pristine - take it!
            if (item.getFrom().isEmpty()
                    && item.getStats().size() == 1
                    && !item.getTags().contains("GoldPer")
                    && !item.getTags().contains("Consumable")
                    && !item.isConsumeOnFull()
                    && !item.isConsumed()
                    && !item.isHideFromAll()
                    && !item.getTags().contains("lane")
                    && (item.getMaps().containsKey(MapType.Rift) && item.getMaps().get(MapType.Rift))) {
                analyzableItems.add(item);
            }
        }

        // We found pristine items, return them!
        if (!analyzableItems.isEmpty()) {
            return analyzableItems;
        }

        for (ItemDto item : itemsWithStat) {
            // Eligible items must:
            // Not have any FROM items that have the same stat
            boolean eligible = true;
            for (Integer fromItemId : item.getFrom()) {
                ItemDto fromItem = this.itemListDto.getData().get(fromItemId);
                if (fromItem.getStats().containsKey(stat)) {
                    eligible = false;
                    break;
                }
            }
            if (eligible
                    && !item.getTags().contains("GoldPer")
                    && !item.getTags().contains("Consumable")
                    && !item.isConsumeOnFull()
                    && !item.isConsumed()
                    && !item.isHideFromAll()
                    && !(item.getPlaintext() != null && item.getPlaintext().contains("starting"))
                    && (item.getMaps().containsKey(MapType.Rift) && item.getMaps().get(MapType.Rift))) {
                analyzableItems.add(item);
            }
        }

        for (ItemDto item : analyzableItems) {
            boolean eligible = true;
            for (ItemStat itemStat : item.getStats().keySet()) {
                if (itemStat != stat && statsToAnalyze.contains(itemStat)) {
                    eligible = false;
                    break;
                }
            }
            if (!eligible) {
                return Collections.emptySet();
            }
        }

        return analyzableItems;
    }

    public Double getAverageStatCost(ItemStat stat) {
        return this.averageStatCosts.get(stat);
    }

    public Role calculateRoleFromBuild(Collection<Integer> buildItems) {
        int adStat = 0;
        int apStat = 0;
        int tankStat = 0;
        float totalItems = buildItems.size();
        float totalSupportItems = 0;
        for (Integer itemId : buildItems) {
            ItemDto item = this.itemListDto.getData().get(itemId);
            if (item.isSupportItem()) {
                totalSupportItems++;
            }
            if (item.getTags() != null && item.getTags().contains("Boots")) {
                totalItems--;
            }
            for (Entry<ItemStat, Double> stat : item.getStats().entrySet()) {
                ItemStat curStat = stat.getKey();
                Double statAmount = stat.getValue();
                Double effectiveAmount = statAmount * averageStatCosts.get(curStat);
                if (curStat.isTankStat()) {
                    tankStat += effectiveAmount;
                } else if (curStat.isApStat()) {
                    apStat += effectiveAmount;
                } else if (curStat.isAdStat()) {
                    adStat += effectiveAmount;
                }
            }
        }
        System.out.print("tank: " + tankStat + " ap: " + apStat + " ad: " + adStat);

        if (totalItems > 0 && totalSupportItems / totalItems >= 0.4) {
            return Role.SUPPORT;
        }

        return this.getRoleFromStatCounts(tankStat, apStat, adStat);
    }

    private Role getRoleFromStatCounts(double tankStat, double apStat, double adStat) {
        double totalStats = tankStat + apStat + adStat;
        double tankRatio = tankStat / totalStats;
        double apRatio = apStat / totalStats;
        double adRatio = adStat / totalStats;

        if (apRatio >= 2 * tankRatio && apRatio >= 2 * adRatio) {
            return Role.AP_CARRY;
        }
        if (adRatio >= 2 * tankRatio && adRatio >= 2 * apRatio) {
            return Role.AD_CARRY;
        }
        if (tankRatio >= 2 * adRatio && tankRatio >= 2 * apRatio) {
            return Role.TANK;
        }
        if (adRatio > tankRatio && apRatio > tankRatio) {
            return Role.HYBRID;
        }
        if (tankRatio > apRatio && adRatio > apRatio) {
            return Role.AD_TANK;
        }
        if (tankRatio > adRatio && apRatio > adRatio) {
            return Role.AP_TANK;
        }

        return Role.UNDETERMINED;
    }
}
