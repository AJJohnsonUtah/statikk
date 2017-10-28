import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ChampionsRoutingModule } from './champions-routing.module';
import { ChampionsComponent } from './champions.component';
import { ChampionDetailComponent } from './champion-detail/champion-detail.component';
import { SharedModule } from '../../shared/shared.module';
import { ChampionSortPipe } from './champion-sort.pipe';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    ChampionsRoutingModule,
    NgbModule,
    SharedModule
  ],
  declarations: [ChampionsComponent, ChampionDetailComponent, ChampionSortPipe]
})
export class ChampionsModule { }
