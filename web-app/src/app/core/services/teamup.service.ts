import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { SignificantTeamups } from '../../shared/models/statikk-api-types/significant-teamups';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class TeamupService extends HttpService {

  public getSignificantTeamups(championId: number): Observable<SignificantTeamups> {
    const championUrl: string = this.apiRoot + '/teamup/significant/by-champion-id/' + championId;
    return this.httpCacheService.get<SignificantTeamups>(championUrl);
  }


}
