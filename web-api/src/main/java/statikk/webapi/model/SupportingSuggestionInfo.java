/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statikk.webapi.model;

/**
 *
 * @author Grann
 */
public class SupportingSuggestionInfo<T> {

    SupportingSuggestionReason supportingReason;
    T supportingInfo;

    public SupportingSuggestionInfo(SupportingSuggestionReason supportingReason, T supportingInfo) {
        this.supportingReason = supportingReason;
        this.supportingInfo = supportingInfo;
    }

    public SupportingSuggestionReason getSupportingReason() {
        return supportingReason;
    }

    public T getSupportingInfo() {
        return supportingInfo;
    }

}
