import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../core/services/static-data.service';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { ChampionPick } from './models/champion-pick';
@Component({
  selector: 'app-team-builder',
  templateUrl: './team-builder.component.html',
  styleUrls: ['./team-builder.component.scss']
})
export class TeamBuilderComponent implements OnInit {

  playersPerTeam = 5;
  currentPick = 0;
  teamAChampions: ChampionPick[] = [];
  teamBChampions: ChampionPick[] = [];
  firstPickTeam: ChampionPick[] = [];
  lastPickTeam: ChampionPick[];
  userPick: ChampionPick;

  staticChampionIds: string[] = [];

  @ViewChild('t') public tooltip: NgbTooltip;

  constructor(private staticDataService: StaticDataService) { }

  ngOnInit() {
    this.tooltip.open();
    this.staticDataService.getChampions().subscribe((staticChampions) => {
      this.staticChampionIds = Object.keys(staticChampions);
    });
    for (let i = 0; i < this.playersPerTeam; i++) {
      this.teamAChampions.push({ championId: null, summonerName: null });
      this.teamBChampions.push({ championId: null, summonerName: null });
    }
  }

  selectUserPick(pick: ChampionPick) {
    this.userPick = pick;
    this.tooltip.close();
  }

}
