
import {Injectable} from '@angular/core';
import {Headers, Http} from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {StaticChampion} from './api-types/static-champion';

@Injectable()
export class StaticDataService {
    private championsUrl = 'http://localhost:8080/api/static-data/champions';  // URL to web api

    constructor(private http: Http) {}

    getChampions(): Promise<StaticChampion[]> {
        return this.http.get(this.championsUrl)
            .toPromise()
            .then(response => response.json().data as StaticChampion[])
            .catch(this.handleError);
    }

    private handleError(error: any): Promise<any> {
        console.error('Error loading champions', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }
}