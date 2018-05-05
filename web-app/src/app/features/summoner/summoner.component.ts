import { Component, OnInit, Pipe } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Location } from '@angular/common';
import 'rxjs/add/operator/switchMap';
import { SummonerDto } from '../../shared/models/riot-api-types/summoner-dto';
import { MatchlistDto } from '../../shared/models/riot-api-types/matchlist-dto';
import { ChampionCount } from '../../shared/models/statikk-api-types/champion-count';
import { LaneCount } from '../../shared/models/statikk-api-types/lane-count';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { StaticDataService } from '../../core/services/static-data.service';
import { SummonerDataService } from '../../core/services/summoner-data.service';

@Component({
  selector: 'app-summoner',
  styleUrls: ['./summoner.component.css'],
  templateUrl: './summoner.component.html',

})

export class SummonerComponent implements OnInit {
  public summonerData: SummonerDto;
  public matchlist: MatchlistDto;
  public champMatchCount: ChampionCount[];
  public laneMatchCount: LaneCount[];
  public staticChampions: Map<String, StaticChampion>;
  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private staticDataService: StaticDataService,
    private summonerDataService: SummonerDataService
  ) { }

  public ngOnInit() {
    this.route.params
      .subscribe((params: Params) => this.loadSummonerData(params['summonerName']));
    this.staticDataService.getChampions()
      .subscribe((championData) => this.staticChampions = championData);
  }

  private loadSummonerData(summonerName: string): void {
    this.summonerDataService.getSummonerData(summonerName)
      .subscribe((summonerData: SummonerDto) => {
        this.summonerData = summonerData;
        this.loadMatchlist();
      });
  }

  private loadMatchlist(): void {
    this.summonerDataService.getMatchList(this.summonerData.accountId)
      .subscribe((matchlist) => {
        this.matchlist = matchlist;
        this.calculateMatchCounts();
      }
      );
  }

  private calculateMatchCounts(): void {
    const laneCounts = new Map<string, number>();
    const champCount = new Map<string, number>();
    this.champMatchCount = [];
    this.laneMatchCount = [];
    for (const match of this.matchlist.matches) {
      const champId = match.champion;
      if (champCount[champId]) {
        champCount[champId]++;
      } else {
        champCount[champId] = 1;
      }
      if (!match.lane) {
        continue;
      }
      if (laneCounts[match.lane]) {
        laneCounts[match.lane]++;
      } else {
        laneCounts[match.lane] = 1;
      }
    }

    for (const champId in champCount) {
      if (champCount.hasOwnProperty(champId)) {
        const championCount = new ChampionCount();
        championCount.championId = +champId;
        championCount.playedCount = champCount[champId];
        this.champMatchCount.push(championCount);
      }
    }

    for (const lane in laneCounts) {
      if (laneCounts.hasOwnProperty(lane)) {
        const laneCount = new LaneCount();
        laneCount.lane = lane;
        laneCount.playedCount = laneCounts[lane];
        this.laneMatchCount.push(laneCount);
      }
    }

    this.champMatchCount.sort((a, b) => b.playedCount - a.playedCount);
    this.laneMatchCount.sort((a, b) => b.playedCount - a.playedCount);
  }

}
