import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../core/services/static-data.service';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { ChampionPick } from './models/champion-pick';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup } from '@angular/forms';
import { laneList } from '../../shared/models/statikk-api-types/filter-criteria/lane';
import { TeamBuilderService } from './services/team-builder.service';
import { ChampionSuggestion } from './models/champion-suggestion';

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
  firstPickTeam: ChampionPick[];
  lastPickTeam: ChampionPick[];
  userPick: ChampionPick;
  userPickForm: FormGroup;
  laneList = laneList;
  championSuggestions: Map<number, ChampionSuggestion>;

  currentTeamPick: number = null;

  staticChampionIds: string[] = [];

  constructor(private staticDataService: StaticDataService, private modalService: NgbModal,
    private formBuilder: FormBuilder, private teamBuilderService: TeamBuilderService) { }

  ngOnInit() {
    this.staticDataService.getChampions().subscribe((staticChampions) => {
      this.staticChampionIds = Object.keys(staticChampions);
    });
    for (let i = 0; i < this.playersPerTeam; i++) {
      this.teamAChampions.push({ championId: null, summonerName: null, lane: null });
      this.teamBChampions.push({ championId: null, summonerName: null, lane: null });
    }
    this.userPickForm = this.formBuilder.group({
      'userSummonerName': '',
      'userLane': ''
    });
  }


  selectUserPick(pick: ChampionPick, modalContent) {
    this.modalService.open(modalContent).result.then((result) => {
      this.userPick = pick;
      this.userPick.lane = result.userLane;
      this.userPick.summonerName = result.userSummonerName;
      this.teamBuilderService.getChampionSuggestions(this.userPick.summonerName, this.userPick.lane).subscribe((suggestionResults) => {
        this.championSuggestions = suggestionResults;
      });
    }, (dismissInfo) => {
    });
  }

  setTeamOrder(team1: ChampionPick[], team2: ChampionPick[]) {
    if (this.userPick) {
      this.firstPickTeam = team1;
      this.lastPickTeam = team2;
      this.currentTeamPick = 0;
    }
  }

  pickChampion(championId: number) {
    // Get current pick from pick number
    const currentPick = this.getCurrentChampionPick();

    // Assign champ id to pick
    currentPick.championId = championId;
    this.currentTeamPick++;
    if (this.currentTeamPick >= 10) {
      this.currentTeamPick = null;
    }
  }

  getCurrentChampionPick() {
    switch (this.currentTeamPick) {
      case 0: return this.firstPickTeam[0];
      case 1: return this.lastPickTeam[0];
      case 2: return this.lastPickTeam[1];
      case 3: return this.firstPickTeam[1];
      case 4: return this.firstPickTeam[2];
      case 5: return this.lastPickTeam[2];
      case 6: return this.lastPickTeam[3];
      case 7: return this.firstPickTeam[3];
      case 8: return this.firstPickTeam[4];
      case 9: return this.lastPickTeam[4];
    }

  }

}
