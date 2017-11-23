import { Injectable } from '@angular/core';
import { FilterCriteriaGroup } from '../../shared/models/statikk-api-types/filter-criteria/filter-criteria-group';
import { HttpParams } from '@angular/common/http';

export class FilterCriteriaService {

  public static getUrlParams(filterCriteriaGroup: FilterCriteriaGroup): HttpParams {
    let params: HttpParams = new HttpParams();
    if (filterCriteriaGroup) {
      for (const criteria in filterCriteriaGroup) {
        if (filterCriteriaGroup.hasOwnProperty(criteria) && filterCriteriaGroup[criteria]) {
          params = params.append(criteria, filterCriteriaGroup[criteria]);
        }
      }
    }
    return params;
  }

}
