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
public class Observer implements Serializable {

    private String encryptionKey;

    public String getEncryptionKey() {
        return encryptionKey;
    }
}
