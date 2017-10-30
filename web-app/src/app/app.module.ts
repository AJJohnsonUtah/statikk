import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ChampionsComponent } from './features/champions/champions.component';
import { ChampionDetailComponent } from './features/champions/champion-detail/champion-detail.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { Http, HttpModule } from '@angular/http';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { StaticDataService } from './shared/services/static-data.service';
import { ChampionWinRateService } from './shared/services/champion-win-rate.service';
import { SummonerDataService } from './shared/services/summoner-data.service';
import { HomeComponent } from './features/home/home.component';

@NgModule({
  declarations: [
    AppComponent, HomeComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule, FormsModule, SharedModule, NgbModule.forRoot(), CoreModule, HttpModule
  ],
  providers: [StaticDataService, ChampionWinRateService, SummonerDataService],
  bootstrap: [AppComponent]
})
export class AppModule { }
