/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.service;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class RiotApiKeyLimitService {

    private long tenSecondResetTime, tenMinuteResetTime;
    private final long TEN_SECONDS = 10 * 1000;
    private final long TEN_MINUTES = 10 * 1000 * 60;

    private final int LIMIT_PER_TEN_SECONDS = 10;
    private final int LIMIT_PER_TEN_MINUTES = 500;

    private long requestsMade;

    private int requestsInTenSeconds, requestsInTenMinutes;

    public RiotApiKeyLimitService() {
        tenSecondResetTime = System.currentTimeMillis() + TEN_SECONDS;
        tenMinuteResetTime = System.currentTimeMillis() + TEN_MINUTES;
        requestsInTenSeconds = 0;
        requestsInTenMinutes = 0;
        requestsMade = 0;
    }

    public void makeApiRequest() {
        if (requestsInTenMinutes >= LIMIT_PER_TEN_MINUTES) {
            System.out.println("Ten minute waiting...");
            waitUntilTime(tenMinuteResetTime + (TEN_SECONDS * 6));
        } else if (requestsInTenSeconds >= LIMIT_PER_TEN_SECONDS) {
            System.out.println("Ten second waiting...");
            waitUntilTime(tenSecondResetTime + TEN_SECONDS);
        }
        long currentTime = System.currentTimeMillis();
        if (tenSecondResetTime < currentTime) {
            tenSecondResetTime = currentTime + TEN_SECONDS;
            requestsInTenSeconds = 0;
        }
        if (tenMinuteResetTime < currentTime) {
            tenMinuteResetTime = currentTime + TEN_MINUTES;
            requestsInTenMinutes = 0;
        }
        requestsInTenSeconds++;
        requestsInTenMinutes++;
        requestsMade++;
        System.out.println(requestsMade);
    }

    private void waitUntilTime(long time) {
        try {
            Thread.sleep(time - System.currentTimeMillis());
        } catch (InterruptedException ex) {
            Logger.getLogger(RiotApiKeyLimitService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void waitMinutes() {
        Logger.getLogger(RiotApiKeyLimitService.class
                .getName()).log(Level.INFO, "Waiting for the minutes to make more requests.");
        waitUntilTime(System.currentTimeMillis() + TEN_MINUTES);
    }

    public void waitSeconds() {
        waitUntilTime(System.currentTimeMillis() + TEN_SECONDS);
    }

    public long getTotalRequestsMade() {
        return requestsMade;
    }

    public void handleLimitHeader(String limitHeader) {
        // "1:10,2:600"

        Logger.getLogger(RiotApiKeyLimitService.class
                .getName()).log(Level.INFO, limitHeader);
        String[] limits = limitHeader.trim().split(",");
        String[] seconds = limits[0].split(":");
        String[] minutes = limits[1].split(":");

        if (Integer.parseInt(minutes[0]) >= LIMIT_PER_TEN_MINUTES) {
            waitMinutes();
        } else if (Integer.parseInt(seconds[0]) >= LIMIT_PER_TEN_SECONDS) {
            waitSeconds();
        }

    }
}
