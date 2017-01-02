/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.main;

import com.njin.loltheory.riotapi.service.RiotApiService;
import com.njin.loltheoryminer.service.MatchMiningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author AJ
 */
@ComponentScan(basePackages = "com.njin")
public class Application {

    @Autowired
    MatchMiningService matchMiningService;

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext(Application.class);
        Application p = context.getBean(Application.class);
        p.start();
    }

    public void start() {

        matchMiningService.mineMatches(100);
    }
}
