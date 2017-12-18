import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/observable/of';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { StaticChampionDetail } from '../../shared/models/riot-api-types/static-champion-detail';
import { HttpService } from './http.service';
import { Observable } from 'rxjs/Observable';
import { StaticChampionsData } from '../../shared/models/riot-api-types/static-champions-data';
import { RealmDto } from '../../shared/models/riot-api-types/realm-dto';

@Injectable()
export class StaticDataService extends HttpService {

  public getChampion(championId: number): Observable<StaticChampionDetail> {
    const championUrl: string = this.apiRoot + '/static-data/champion/' + championId;
    return this.httpCacheService.get<StaticChampionDetail>(championUrl);
  }

  public getChampions(): Observable<Map<string, StaticChampion>> {
    const championsUrl: string = this.apiRoot + '/static-data/champions';
    return this.httpCacheService.get<{ data: Map<string, StaticChampion> }>(championsUrl).map((response) => {
      return response.data;
    });
  }

  public getRealmsData(): Observable<RealmDto> {
    const realmsUrl: string = this.apiRoot + '/static-data/realms';
    return this.httpCacheService.get<RealmDto>(realmsUrl);
  }
}
