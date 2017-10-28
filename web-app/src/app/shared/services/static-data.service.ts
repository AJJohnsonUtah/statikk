import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { StaticChampion } from '../models/riot-api-types/static-champion';
import { StaticChampionDetail } from '../models/riot-api-types/static-champion-detail';

@Injectable()
export class StaticDataService {

  private apiEndpoint = 'http://' + window.location.hostname + ':8080/api';
  private _cachedChampionsData: Promise<Map<string, StaticChampion>> = null;
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
    if (this._cachedChampionsData !== null) {
      return this._cachedChampionsData;
    }
    const championsUrl: string = this.apiEndpoint + '/static-data/champions';
    this._cachedChampionsData = this.http.get(championsUrl)
      .toPromise()
      .then(
      (response) => response.json().data as Map<string, StaticChampion>
      )
      .catch(this.handleError);
    return this._cachedChampionsData;
  }

  private handleError(error: any): Promise<any> {
    console.error('Error loading static data', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
