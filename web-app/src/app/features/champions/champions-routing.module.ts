import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChampionDetailComponent } from './champion-detail/champion-detail.component';
import { ChampionsComponent } from './champions.component';

const routes: Routes = [
  { path: '', component: ChampionsComponent },
  { path: ':id', component: ChampionDetailComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ChampionsRoutingModule { }
