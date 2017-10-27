/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.domain.riotapi.model;

import java.io.Serializable;

/**
 *
 * @author AJ
 */
public class MatchReferenceDto implements Serializable {

    private int champion;
    private Region platformId;
    private int season;
    private QueueType queue;
    private String role;
    private String lane;
    private long timestamp;
    private long gameId;

    public MatchReferenceDto(){};
    
    public int getChampionId() {
        return champion;
    }

    public Region getPlatformId() {
        return platformId;
    }

    public int getSeason() {
        return season;
    }

    public QueueType getQueue() {
        return queue;
    }

    public String getRole() {
        return role;
    }

    public String getLane() {
        return lane;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getGameId() {
        return gameId;
    }

    
}
