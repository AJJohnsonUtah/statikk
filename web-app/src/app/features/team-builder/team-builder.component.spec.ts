import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamBuilderComponent } from './team-builder.component';
import { Component, Input, NO_ERRORS_SCHEMA } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../core/services/static-data.service';
import { NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap/rating/rating-config';
import { of } from 'rxjs/observable/of';
import { ReactiveFormsModule } from '@angular/forms';
import { TeamBuilderService } from './services/team-builder.service';
import { ChampionSuggestion } from './models/champion-suggestion';

describe('TeamBuilderComponent', () => {
  let component: TeamBuilderComponent;
  let fixture: ComponentFixture<TeamBuilderComponent>;

  @Component({ selector: 'app-champion-image', template: '' })
  class AppChampionImageMockComponent {
    @Input()
    championId: number;
  }

  class MockStaticDataService {
    getChampions() {
      return of({ data: { '1': {}, '2': {} } });
    }
  }

  class MockTeamBuilderService {
    getSuggestions() {
      return of({ 1: { championId: 1, score: 0.1 } });
    }
  }

  @Component({ selector: 'app-suggestion-context', template: '' })
  class AppSuggestionContextMockComponent {
    @Input()
    championId: number;
  }

  @Component({ selector: 'app-champion-suggestion-group', template: '' })
  class AppChampionSuggestionGroupMockComponent {
    @Input()
    championSuggestions: ChampionSuggestion[];
    @Input()
    rating: number;
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgbModule.forRoot(), ReactiveFormsModule],
      declarations: [TeamBuilderComponent, AppChampionImageMockComponent, AppSuggestionContextMockComponent,
        AppChampionSuggestionGroupMockComponent],
      providers: [{ provide: StaticDataService, useClass: MockStaticDataService },
      { provide: TeamBuilderService, useClass: MockTeamBuilderService }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamBuilderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
