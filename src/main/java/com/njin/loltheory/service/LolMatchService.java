/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.model.LolMatch;

/**
 *
 * @author AJ
 */
public interface LolMatchService {

    void createLolMatch(LolMatch lolMatch);

    void updateLolMatch(LolMatch lolMatch);
}
