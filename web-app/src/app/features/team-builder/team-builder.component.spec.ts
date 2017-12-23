import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamBuilderComponent } from './team-builder.component';
import { Component, Input, NO_ERRORS_SCHEMA } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { StaticDataService } from '../../core/services/static-data.service';
import { NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap/rating/rating-config';
import { of } from 'rxjs/observable/of';
import { ReactiveFormsModule } from '@angular/forms';

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

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [NgbModule.forRoot(), ReactiveFormsModule],
      declarations: [TeamBuilderComponent, AppChampionImageMockComponent],
      providers: [{ provide: StaticDataService, useClass: MockStaticDataService }]
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
