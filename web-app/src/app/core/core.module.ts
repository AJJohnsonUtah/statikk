import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaticDataService } from './services/static-data.service';
import { ChampionWinRateService } from './services/champion-win-rate.service';
import { SummonerDataService } from './services/summoner-data.service';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  imports: [
    CommonModule,
    HttpClientModule
  ],
  declarations: [],
  providers: [StaticDataService, ChampionWinRateService, SummonerDataService]
})
export class CoreModule { }
