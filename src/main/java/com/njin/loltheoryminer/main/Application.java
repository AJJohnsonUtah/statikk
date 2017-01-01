/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheoryminer.main;

import com.njin.loltheory.model.ChampSpec;
import com.njin.loltheory.service.ChampSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author AJ
 */
@ComponentScan(basePackages = "com.njin")
public class Application {
    @Autowired
    private ChampSpecService champSpecService;
    
    public static void main(String[] args) {
        
        ApplicationContext context = 
        new AnnotationConfigApplicationContext(Application.class);
        Application p = context.getBean(Application.class);
        p.start();
    }
    
    public void start() {                
        ChampSpec a = new ChampSpec();
        a.setChampId(123L);
        champSpecService.createChampSpec(a);
        ChampSpec b = champSpecService.findChampSpec(1L);
        System.out.println(b.getChampId() + " WE FUCKING FOUND IT!");
    }
}
