import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ChampionSuggestion } from '../../models/champion-suggestion';

@Component({
  selector: 'app-champion-suggestion-group',
  templateUrl: './champion-suggestion-group.component.html',
  styleUrls: ['./champion-suggestion-group.component.scss']
})
export class ChampionSuggestionGroupComponent implements OnInit {

  constructor() { }

  @Input()
  rating: number;

  @Input()
  championSuggestions: ChampionSuggestion[];

  @Output()
  selectChampionEvent = new EventEmitter();

  ngOnInit() {
  }

  public selectChampion(championId: number) {
    this.selectChampionEvent.emit(championId);
  }

}
