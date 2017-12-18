import { Component, OnInit } from '@angular/core';
import { StaticDataService } from '../../core/services/static-data.service';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';

@Component({
  selector: 'app-team-builder',
  templateUrl: './team-builder.component.html',
  styleUrls: ['./team-builder.component.scss']
})
export class TeamBuilderComponent implements OnInit {

  playersPerTeam = 5;
  currentPick = 0;
  teamAChampions: number[];
  teamBChampions: number[];
  staticChampionIds: string[] = [];

  constructor(private staticDataService: StaticDataService) { }

  ngOnInit() {
    this.staticDataService.getChampions().subscribe((staticChampions) => {
      this.staticChampionIds = Object.keys(staticChampions);
    });
    this.teamAChampions = new Array<number>(5);
    this.teamBChampions = new Array<number>(5);
  }

}
