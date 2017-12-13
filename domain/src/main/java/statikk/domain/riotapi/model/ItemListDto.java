/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import statikk.domain.entity.LolVersion;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AJ
 */
public class ItemListDto implements Serializable {

    BasicDataDto basic;
    Map<Integer, ItemDto> data;
    List<GroupDto> groups;
    List<ItemTreeDto> tree;
    String type;
    LolVersion version;
    
    public BasicDataDto getBasic() {
        return basic;
    }

    public Map<Integer, ItemDto> getData() {
        return data;
    }

    public List<GroupDto> getGroups() {
        return groups;
    }

    public List<ItemTreeDto> getTree() {
        return tree;
    }

    public String getType() {
        return type;
    }

    public LolVersion getVersion() {
        return version;
    }
    
}
