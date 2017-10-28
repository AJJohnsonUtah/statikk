import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { SummonerDto } from '../models/riot-api-types/summoner-dto';
import { MatchlistDto } from '../models/riot-api-types/matchlist-dto';

@Injectable()
export class SummonerDataService {
  private apiEndpoint = 'http://' + window.location.hostname + ':8080/api';
  private cachedSummonerData: Map<string, Promise<SummonerDto>> = new Map();

  constructor(private http: Http) { }

  public getSummonerData(summonerName: string): Promise<SummonerDto> {
    const allWinRatesUrl: string = this.apiEndpoint + '/summoner/by-name/' + summonerName;
    if (this.cachedSummonerData[summonerName]) {
      return this.cachedSummonerData[summonerName];
    }
    const summonerResponse = this.http.get(allWinRatesUrl)
      .toPromise()
      .then(
      (response) => response.json() as SummonerDto
      )
      .catch(this.handleError);
    this.cachedSummonerData[summonerName] = summonerResponse;
    return summonerResponse;
  }

  public getMatchList(accountId: number): Promise<MatchlistDto> {
    const matchlistUrl: string = this.apiEndpoint + '/summoner/matchlist/' + accountId;
    return this.http.get(matchlistUrl)
      .toPromise()
      .then(
      (response) => response.json() as MatchlistDto
      )
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Error loading all summoner data', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
