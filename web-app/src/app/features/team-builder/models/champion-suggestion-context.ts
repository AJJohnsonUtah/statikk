import { ChampionSuggestionReason } from './champion-suggestion-reason';
import { SupportingSuggestionInfo } from './supporting-suggestion-info';

export class ChampionSuggestionContext {
    score: number;
    reason: ChampionSuggestionReason;
    supportingInfo: SupportingSuggestionInfo[];
}
