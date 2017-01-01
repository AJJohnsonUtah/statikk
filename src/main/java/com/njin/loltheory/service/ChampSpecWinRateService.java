/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.service;

import com.njin.loltheory.model.ChampSpecWinRate;

/**
 *
 * @author AJ
 */
public interface ChampSpecWinRateService {
    
    void createChampSpecWinRate(ChampSpecWinRate champSpecWinRate);

    void updateChampSpecWinRate(ChampSpecWinRate champSpecWinRate);
}
