import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChampionSuggestionGroupComponent } from './champion-suggestion-group.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { SuggestionScoreFilterPipe } from '../../pipes/suggestion-score-filter.pipe';
import { ChampionSuggestionComponent } from '../champion-suggestion/champion-suggestion.component';
import { SuggestionContextComponent } from '../suggestion-context/suggestion-context.component';
import { ChampionImageComponent } from '../../../../shared/components/champion-image/champion-image.component';

describe('ChampionSuggestionGroupComponent', () => {
  let component: ChampionSuggestionGroupComponent;
  let fixture: ComponentFixture<ChampionSuggestionGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChampionSuggestionGroupComponent, SuggestionScoreFilterPipe,
        ChampionSuggestionComponent, SuggestionContextComponent, ChampionImageComponent],
      imports: [NgbModule.forRoot()]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChampionSuggestionGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
