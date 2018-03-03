import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StaticDataService } from './services/static-data.service';
import { ChampionWinRateService } from './services/champion-win-rate.service';
import { SummonerDataService } from './services/summoner-data.service';
import { HttpClientModule } from '@angular/common/http';
import { ApiStatusService } from './services/api-status.service';
import { HttpCacheService } from './services/http-cache.service';
import { NavbarComponent } from './components/navbar/navbar.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { RouterModule } from '@angular/router';
import { TeamupService } from './services/teamup.service';
import { MatchupService } from './services/matchup.service';


@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    NgbModule,
    RouterModule
  ],
  declarations: [NavbarComponent],
  exports: [NavbarComponent],
  providers: [ApiStatusService, StaticDataService, ChampionWinRateService
    , SummonerDataService, HttpCacheService, TeamupService, MatchupService]
})
export class CoreModule { }
