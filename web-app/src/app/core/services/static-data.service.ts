import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { StaticChampionDetail } from '../../shared/models/riot-api-types/static-champion-detail';
import { HttpService } from './http.service';
import { Observable } from 'rxjs/Observable';
import { StaticChampionsData } from '../../shared/models/riot-api-types/static-champions-data';

@Injectable()
export class StaticDataService extends HttpService {


  public getChampion(championId: number): Observable<StaticChampionDetail> {
    const championUrl: string = this.apiRoot + '/static-data/champion/' + championId;
    return this.httpClient.get<StaticChampionDetail>(championUrl);
  }

  public getChampions(): Observable<{ data: Map<string, StaticChampion> }> {
    const championsUrl: string = this.apiRoot + '/static-data/champions';
    return this.httpClient.get<{ data: Map<string, StaticChampion> }>(championsUrl);
  }

}
