import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { ChampionWinRate } from './statikk-api-types/ChampionWinRate';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class ChampionWinRateService {
    private apiEndpoint = 'http://' + window.location.hostname + '/api';
    constructor(private http: Http) { }

    public getAllChampionWinRates(): Promise<Map<string, ChampionWinRate>> {
        const allWinRatesUrl: string = this.apiEndpoint + '/champion/win-rate/all';
        return this.http.get(allWinRatesUrl)
            .toPromise()
            .then(
            (response) => response.json() as Map<string, ChampionWinRate>
            )
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Error loading all champion win rates data', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}
