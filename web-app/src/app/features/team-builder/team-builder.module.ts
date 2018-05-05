import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeamBuilderRoutingModule } from './team-builder-routing.module';
import { TeamBuilderComponent } from './team-builder.component';
import { SharedModule } from '../../shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TeamBuilderService } from './services/team-builder.service';
import { SuggestionContextComponent } from './components/suggestion-context/suggestion-context.component';
import { ChampionSuggestionComponent } from './components/champion-suggestion/champion-suggestion.component';
import { ChampionSuggestionGroupComponent } from './components/champion-suggestion-group/champion-suggestion-group.component';
import { SuggestionScoreFilterPipe } from './pipes/suggestion-score-filter.pipe';
import { ChampionSearchPipe } from './pipes/champion-search.pipe';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    TeamBuilderRoutingModule,
    SharedModule
  ],
  declarations: [TeamBuilderComponent, SuggestionContextComponent
    , ChampionSuggestionComponent, ChampionSuggestionGroupComponent, SuggestionScoreFilterPipe, ChampionSearchPipe],
  providers: [TeamBuilderService]
})
export class TeamBuilderModule { }
