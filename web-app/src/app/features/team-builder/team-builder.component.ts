import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-team-builder',
  templateUrl: './team-builder.component.html',
  styleUrls: ['./team-builder.component.css']
})
export class TeamBuilderComponent implements OnInit {

  playersPerTeam: number = 5;
  currentPick: number = 0;
  teamAChampions: number[];
  teamBChampions: number[];

  constructor() { }

  ngOnInit() {
    this.teamAChampions = new Array<number>(5);
    this.teamBChampions = new Array<number>(5);
  }

}
