import { Component, OnInit, Input } from '@angular/core';
import { ChampionSuggestionContext } from '../../models/champion-suggestion-context';
import { ChampionSuggestionReason } from '../../models/champion-suggestion-reason';
import { SupportingSuggestionReason } from '../../models/supporting-suggestion-reason';

@Component({
  selector: 'app-suggestion-context',
  templateUrl: './suggestion-context.component.html',
  styleUrls: ['./suggestion-context.component.scss']
})
export class SuggestionContextComponent implements OnInit {

  constructor() { }

  championSuggestionReason = ChampionSuggestionReason;
  supportingSuggestionReason = SupportingSuggestionReason;

  @Input()
  suggestionContext: ChampionSuggestionContext[];

  @Input()
  championId: number;

  ngOnInit() {
  }

}
