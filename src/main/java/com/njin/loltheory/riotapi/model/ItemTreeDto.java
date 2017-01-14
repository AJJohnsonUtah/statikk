/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author AJ
 */
public class ItemTreeDto implements Serializable {

    String header;
    List<String> tags;

    public String getHeader() {
        return header;
    }

    public List<String> getTags() {
        return tags;
    }

}
