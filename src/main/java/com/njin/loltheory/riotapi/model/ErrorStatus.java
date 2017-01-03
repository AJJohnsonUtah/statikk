/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.riotapi.model;

/**
 *
 * @author AJ
 */
public class ErrorStatus {

    private String message;
    private Integer status_code;

    public String getMessage() {
        return message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

}
