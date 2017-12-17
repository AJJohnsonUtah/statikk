import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChampionImageComponent } from './champion-image.component';

describe('ChampionImageComponent', () => {
  let component: ChampionImageComponent;
  let fixture: ComponentFixture<ChampionImageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChampionImageComponent ]
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
