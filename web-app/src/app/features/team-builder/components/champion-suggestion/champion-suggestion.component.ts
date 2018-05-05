import { Component, OnInit, Input } from '@angular/core';
import { ChampionSuggestion } from '../../models/champion-suggestion';

@Component({
  selector: 'app-champion-suggestion',
  templateUrl: './champion-suggestion.component.html',
  styleUrls: ['./champion-suggestion.component.scss']
})
export class ChampionSuggestionComponent implements OnInit {

  constructor() { }

  @Input()
  championSuggestion: ChampionSuggestion;

  @Input()
  championId: number;

  ngOnInit() {
  }

}
