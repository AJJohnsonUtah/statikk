import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { StaticChampion } from './riot-api-types/static-champion';
import { StaticChampionDetail } from './riot-api-types/static-champion-detail';

@Injectable()
export class StaticDataService {

  private apiEndpoint = 'http://' + window.location.hostname + '/api';
  private _cachedChampionsData: Map<string, StaticChampion>;
  constructor(private http: Http) { }

  public getChampion(championId: number): Promise<StaticChampionDetail> {
    const championUrl: string = this.apiEndpoint + '/static-data/champion/' + championId;
    return this.http.get(championUrl)
      .toPromise()
      .then(
      (response) =>
        response.json() as StaticChampionDetail
      )
      .catch(this.handleError);
  }

  public getChampions(): Promise<Map<string, StaticChampion>> {
    const championsUrl: string = this.apiEndpoint + '/static-data/champions';
    return this.http.get(championsUrl)
      .toPromise()
      .then(
      (response) => this.cacheStaticChampions(response)
      )
      .catch(this.handleError);
  }

  private cacheStaticChampions(response: any): Map<string, StaticChampion> {
    let data = response.json().data;
    this._cachedChampionsData = data;
    return data as Map<string, StaticChampion>;
  }

  private handleError(error: any): Promise<any> {
    console.error('Error loading static data', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
