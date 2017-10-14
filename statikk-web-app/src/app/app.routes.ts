import { Routes } from '@angular/router';
import { HomeComponent } from './home';
import { AboutComponent } from './about';
import { NoContentComponent } from './no-content';
import { ChampionsComponent } from './champions';
import { ChampionDetailComponent } from './champions/champion-detail/champion-detail.component';

export const ROUTES: Routes = [
  { path: '', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'champions', component: ChampionsComponent },
  { path: 'champion/:id', component: ChampionDetailComponent },
  { path: 'barrel', loadChildren: './+barrel#BarrelModule' },
  { path: '**', component: NoContentComponent },
];
