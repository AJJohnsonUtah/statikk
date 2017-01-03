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
public class Timeline implements Serializable {

    private long frameInterval;
    private List<Frame> frames;

    public long getFrameInterval() {
        return frameInterval;
    }

    public List<Frame> getFrames() {
        return frames;
    }

}
