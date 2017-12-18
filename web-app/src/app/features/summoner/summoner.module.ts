import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SummonerRoutingModule } from './summoner-routing.module';
import { SummonerComponent } from './summoner.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    SummonerRoutingModule,
    SharedModule
  ],
  declarations: [SummonerComponent]
})
export class SummonerModule { }
