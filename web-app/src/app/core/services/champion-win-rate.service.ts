import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/champion-win-rate';
import { HttpService } from './http.service';
import { WinRateWithTotal } from '../../shared/models/statikk-api-types/win-rate-with-total';
import { FormGroup } from '@angular/forms/src/model';
import { BaseWinRate } from '../../shared/models/statikk-api-types/base-win-rate';
import { HttpClient } from '@angular/common/http/src/client';
import { FilterCriteriaGroup } from '../../shared/models/statikk-api-types/filter-criteria/filter-criteria-group';
import { FilterCriteriaService } from './filter-criteria.service';


@Injectable()
export class ChampionWinRateService extends HttpService {

    private championWinRateUrl = this.apiRoot + '/champion/win-rate';

    public getAllChampionWinRates(filterCriteriaGroup: FilterCriteriaGroup): Observable<WinRateWithTotal<ChampionWinRate>> {
        const allWinRatesUrl = this.championWinRateUrl + '/all';
        return this.httpClient.get<WinRateWithTotal<ChampionWinRate>>(allWinRatesUrl,
            { params: FilterCriteriaService.getUrlParams(filterCriteriaGroup) });
    }

    public getChampionMatchTypeWinRates(championId: number,
        filterCriteriaGroup: FilterCriteriaGroup): Observable<Map<string, BaseWinRate>> {
        const url = this.championWinRateUrl + '/' + championId;
        return this.httpClient.get<Map<string, BaseWinRate>>(url,
            { params: FilterCriteriaService.getUrlParams(filterCriteriaGroup) });
    }

    public getChampionLaneWinRates(championId: number,
        filterCriteriaGroup: FilterCriteriaGroup): Observable<Map<string, BaseWinRate>> {
        const url = this.championWinRateUrl + '/by-lane/' + championId;
        return this.httpClient.get<Map<string, BaseWinRate>>(url,
            { params: FilterCriteriaService.getUrlParams(filterCriteriaGroup) });
    }

    public getChampionRoleWinRates(championId: number,
        filterCriteriaGroup: FilterCriteriaGroup): Observable<Map<string, BaseWinRate>> {
        const url = this.championWinRateUrl + '/by-role/' + championId;
        return this.httpClient.get<Map<string, BaseWinRate>>(url,
            { params: FilterCriteriaService.getUrlParams(filterCriteriaGroup) });
    }
}
