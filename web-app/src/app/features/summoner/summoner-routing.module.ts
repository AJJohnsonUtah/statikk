import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SummonerComponent } from './summoner.component';

const routes: Routes = [{
  path: ':summonerName', component: SummonerComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SummonerRoutingModule { }
