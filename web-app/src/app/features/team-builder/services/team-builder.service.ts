import { Injectable } from '@angular/core';
import { HttpService } from '../../../core/services/http.service';
import { ChampionSuggestion } from '../models/champion-suggestion';
import { Observable } from 'rxjs/Observable';
import { ChampionPick } from '../models/champion-pick';
import { TeamBuilderProgress } from '../models/team-builder-progress';

@Injectable()
export class TeamBuilderService extends HttpService {

  public getChampionSuggestions(summonerName: string,
    lane: string,
    allyChampions: ChampionPick[],
    enemyChampions: ChampionPick[]): Observable<ChampionSuggestion[]> {
    const url = this.apiRoot + '/team-builder/suggestions';
    const teamBuilderProgress = new TeamBuilderProgress(summonerName, lane, allyChampions, enemyChampions);
    return this.httpClient.post<ChampionSuggestion[]>(url, teamBuilderProgress);
  }

}
