import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable } from 'rxjs/Observable';
import { SignificantMatchups } from '../../shared/models/statikk-api-types/significant-matchups';

@Injectable()
export class MatchupService extends HttpService {

  public getSignificantMatchups(championId: number): Observable<SignificantMatchups> {
    const championUrl: string = this.apiRoot + '/matchup/significant/by-champion-id/' + championId;
    return this.httpCacheService.get<SignificantMatchups>(championUrl);
  }

}
