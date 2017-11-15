import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/ChampionWinRate';
import { HttpService } from './http.service';


@Injectable()
export class ChampionWinRateService extends HttpService {
    private apiEndpoint = 'http://' + window.location.hostname + ':8080/api';

    public getAllChampionWinRates(): Observable<Map<string, ChampionWinRate>> {
        const allWinRatesUrl: string = this.apiEndpoint + '/champion/win-rate/all';
        return this.httpClient.get<Map<string, ChampionWinRate>>(allWinRatesUrl);
    }
}
