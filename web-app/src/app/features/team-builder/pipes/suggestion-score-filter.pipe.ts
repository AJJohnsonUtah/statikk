import { Pipe, PipeTransform } from '@angular/core';
import { ChampionSuggestion } from '../models/champion-suggestion';

@Pipe({
  name: 'suggestionScoreFilter'
})
export class SuggestionScoreFilterPipe implements PipeTransform {

  transform(value: ChampionSuggestion[], rating: number): ChampionSuggestion[] {
    if (value) {
      return value.filter((suggestion) => {
        if (rating === 1) {
          return suggestion.score <= 1 / 5;
        } else {
          return suggestion.score > rating / 5 - 1 / 5 && suggestion.score <= rating / 5;
        }
      });
    }
  }

}
