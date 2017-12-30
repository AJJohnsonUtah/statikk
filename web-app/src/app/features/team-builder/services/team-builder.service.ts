import { Injectable } from '@angular/core';
import { HttpService } from '../../../core/services/http.service';
import { ChampionSuggestion } from '../models/champion-suggestion';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class TeamBuilderService extends HttpService {

  public getChampionSuggestions(summonerName: string, lane: string): Observable<Map<number, ChampionSuggestion>> {
    const url = this.apiRoot + '/team-builder/suggestions';
    return this.httpClient.post<Map<number, ChampionSuggestion>>(url, { summonerName: summonerName, lane: lane });
  }

}
