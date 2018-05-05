import { async, ComponentFixture, TestBed, } from '@angular/core/testing';

import { ChampionSuggestionComponent } from './champion-suggestion.component';
import { SuggestionContextComponent } from '../suggestion-context/suggestion-context.component';
import { ChampionImageComponent } from '../../../../shared/components/champion-image/champion-image.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../../../core/services/static-data.service';
import { of } from 'rxjs/observable/of';
import { ChampionSuggestion } from '../../models/champion-suggestion';
import { Component, Input } from '@angular/core';

describe('ChampionSuggestionComponent', () => {
  let component: ChampionSuggestionComponent;
  let fixture: ComponentFixture<ChampionSuggestionComponent>;

  @Component({ selector: 'app-champion-image', template: '' })
  class AppChampionImageMockComponent {
    @Input()
    championId: number;
  }
  class MockStaticDataService {
    getChampions() {
      return of({ data: { '1': {}, '2': {} } });
    }

    getRealmsData() {
      return of({ v: '1.2.3' });
    }
  }
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ChampionSuggestionComponent, SuggestionContextComponent, AppChampionImageMockComponent],
      imports: [NgbModule.forRoot()],
      providers: [{ provide: StaticDataService, useClass: MockStaticDataService }]

    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChampionSuggestionComponent);
    component = fixture.componentInstance;
    component.championId = 1;
    component.championSuggestion = { championId: 1, score: 1 } as ChampionSuggestion;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
