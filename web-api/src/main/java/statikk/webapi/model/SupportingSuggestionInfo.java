/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

import statikk.domain.stats.model.CorrelationInfo;

/**
 *
 * @author Grann
 */
public class SupportingSuggestionInfo<T> {

    CorrelationInfo supportingReason;
    T supportingInfo;

    public SupportingSuggestionInfo(CorrelationInfo supportingReason, T supportingInfo) {
        this.supportingReason = supportingReason;
        this.supportingInfo = supportingInfo;
    }

    public CorrelationInfo getSupportingReason() {
        return supportingReason;
    }

    public T getSupportingInfo() {
        return supportingInfo;
    }

}
