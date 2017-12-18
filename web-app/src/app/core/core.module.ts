import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaticDataService } from './services/static-data.service';
import { ChampionWinRateService } from './services/champion-win-rate.service';
import { SummonerDataService } from './services/summoner-data.service';
import { HttpClientModule } from '@angular/common/http';
import { ApiStatusService } from './services/api-status.service';
import { HttpCacheService } from './services/http-cache.service';


@NgModule({
  imports: [
    CommonModule,
    HttpClientModule
  ],
  declarations: [],
  providers: [ApiStatusService, StaticDataService, ChampionWinRateService, SummonerDataService, HttpCacheService]
})
export class CoreModule { }
