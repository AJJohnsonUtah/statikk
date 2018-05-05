import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChampionImageComponent } from './champion-image.component';
import { of } from 'rxjs/observable/of';
import { StaticDataService } from '../../../core/services/static-data.service';

describe('ChampionImageComponent', () => {
  let component: ChampionImageComponent;
  let fixture: ComponentFixture<ChampionImageComponent>;

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
      declarations: [ChampionImageComponent],
      providers: [{ provide: StaticDataService, useClass: MockStaticDataService }]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChampionImageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
