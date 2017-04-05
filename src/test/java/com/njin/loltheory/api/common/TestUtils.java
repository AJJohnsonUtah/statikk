/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.loltheory.api.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author AJ
 */
public class TestUtils {

  public static boolean isJSONValid(String jsonInString ) {
    try {
       final ObjectMapper mapper = new ObjectMapper();
       mapper.readTree(jsonInString);
       return true;
    } catch (IOException e) {
       return false;
    }
  }
}
