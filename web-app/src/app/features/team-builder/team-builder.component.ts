import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { NgbTooltip } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../core/services/static-data.service';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { ChampionPick } from './models/champion-pick';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup } from '@angular/forms';
import { laneList } from '../../shared/models/statikk-api-types/filter-criteria/lane';

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
  userPickForm: FormGroup;
  laneList = laneList;

  staticChampionIds: string[] = [];

  constructor(private staticDataService: StaticDataService, private modalService: NgbModal, private formBuilder: FormBuilder) { }

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
    this.modalService.open(modalContent).result.then((results) => {
      this.userPick = pick;
    }, (dismissInfo) => {
      this.userPick = null;
    });
  }



}
