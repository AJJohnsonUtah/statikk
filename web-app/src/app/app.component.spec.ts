import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { StaticDataService } from './core/services/static-data.service';
import { Component } from '@angular/core';
describe('AppComponent', () => {

  @Component({
    selector: 'app-navbar',
    template: '<p>Statikk</p>'
  })
  class MockAppNavbarComponent { }


  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent, MockAppNavbarComponent
      ],
      imports: [RouterTestingModule],
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it('should render a navbar with app-navbar selector', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('app-navbar').textContent).toContain('Statikk');
  }));
});
