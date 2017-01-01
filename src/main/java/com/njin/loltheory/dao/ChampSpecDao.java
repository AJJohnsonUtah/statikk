/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.dao;

import com.njin.loltheory.model.ChampSpec;

/**
 *
 * @author AJ
 */
public interface ChampSpecDao {

    void createChampSpec(ChampSpec champSpec);

    ChampSpec findChampSpec(Long id);
}
