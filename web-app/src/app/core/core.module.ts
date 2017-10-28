import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Http } from '@angular/http';
import { SummonerDataService } from '../shared/services/summoner-data.service';
import { ChampionWinRateService } from '../shared/services/champion-win-rate.service';
import { StaticDataService } from '../shared/services/static-data.service';


@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [],
  providers: [StaticDataService, ChampionWinRateService, SummonerDataService, Http]
})
export class CoreModule { }
