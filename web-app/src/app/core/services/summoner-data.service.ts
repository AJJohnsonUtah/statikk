import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { SummonerDto } from '../../shared/models/riot-api-types/summoner-dto';
import { MatchlistDto } from '../../shared/models/riot-api-types/matchlist-dto';
import { HttpService } from './http.service';

@Injectable()
export class SummonerDataService extends HttpService {

  public getSummonerData(summonerName: string): Observable<SummonerDto> {
    const allWinRatesUrl: string = this.apiRoot + '/summoner/by-name/' + summonerName;
    return this.httpClient.get<SummonerDto>(allWinRatesUrl);
  }

  public getMatchList(accountId: number): Observable<MatchlistDto> {
    const matchlistUrl: string = this.apiRoot + '/summoner/matchlist/' + accountId;
    return this.httpClient.get<MatchlistDto>(matchlistUrl);
  }
}
