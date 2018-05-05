/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.dataminer.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import statikk.domain.entity.LolVersion;
import statikk.domain.riotapi.model.ItemListDto;
import statikk.domain.riotapi.model.MatchDetail;
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

    Map<String, SpecVersionItemService> specVersionItemServices;

    private String currentMaxVersion = "";
    
    public ItemAnalysisService(RiotApiService riotApiService) {
        this.riotApiService = riotApiService;
    }

    public void reloadItems() {
        this.specVersionItemServices = null;
        this.loadItems();
    }

    /**
     * Populates the item data within the instance of ItemAnalysisService.
     *
     * Final items are populated, along with the itemListDto from the Riot API
     */
    public void loadItems() {
        // Short-circuit return if the items have already been loaded
        if (specVersionItemServices != null) {
            return;
        }
        this.specVersionItemServices = new HashMap<>();
        String[] versions = riotApiService.getVersionsData(Region.NA);
        for (int i = 0; i < 3; i++) {
            String version = versions[i];
            this.currentMaxVersion = (version.compareTo(currentMaxVersion) > 1 ? version: currentMaxVersion);
            System.out.println("Loading item data for version " + version);
            ItemListDto itemListDto = riotApiService.getItemsData(Region.NA, version);
            this.specVersionItemServices.put(version, new SpecVersionItemService(version, itemListDto.getData(), finalBuildOrderService));
        }
    }

    public void loadFinalBuildOrders(MatchDetail matchDetail) {
        String versionToUse = getItemDataVersion(matchDetail.getGameVersion());
        if (!this.specVersionItemServices.containsKey(versionToUse)) {
            throw new RuntimeException("No items data found for version " + matchDetail.getGameVersion() + " (using version " + versionToUse + ")");
        }
        this.specVersionItemServices.get(versionToUse).loadFinalBuildOrders(matchDetail);
    }

    public void loadParticipantRoles(MatchDetail matchDetail) {
        String versionToUse = getItemDataVersion(matchDetail.getGameVersion());
        if (!this.specVersionItemServices.containsKey(versionToUse)) {
            throw new RuntimeException("No items data found for version " + matchDetail.getGameVersion() + " (using version " + versionToUse + ")");
        }
        this.specVersionItemServices.get(versionToUse).loadParticipantRoles(matchDetail);
    }

    public String getItemDataVersion(String gameVersion) {
        String curVersion = "";
        for (String itemDataVersion : this.specVersionItemServices.keySet()) {
            if (itemDataVersion.contains(gameVersion.substring(0, 3)) && itemDataVersion.compareTo(curVersion) > 0) {
                curVersion = itemDataVersion;
            }
        }
        return curVersion;
    }
    
    public LolVersion getMostRecentVersion() {
        return new LolVersion(currentMaxVersion);
    }
}
