import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, Input } from '@angular/core';
import { SuggestionContextComponent } from './suggestion-context.component';

describe('SuggestionContextComponent', () => {
  let component: SuggestionContextComponent;
  let fixture: ComponentFixture<SuggestionContextComponent>;
  @Component({ selector: 'app-champion-image', template: '' })
  class AppChampionImageMockComponent {
    @Input()
    championId: number;
  }
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SuggestionContextComponent, AppChampionImageMockComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SuggestionContextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
