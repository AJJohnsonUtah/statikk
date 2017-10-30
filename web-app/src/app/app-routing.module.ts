import { Routes, RouterModule } from '@angular/router';
import { NoContentComponent } from './core/components/no-content/no-content.component';
import { ChampionsComponent } from './features/champions/champions.component';
import { ChampionDetailComponent } from './features/champions/champion-detail/champion-detail.component';
import { NgModule } from '@angular/core';
import { HomeComponent } from './features/home/home.component';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'champions', loadChildren: 'app/features/champions/champions.module#ChampionsModule' },
    { path: 'summoner', loadChildren: 'app/features/summoner/summoner.module#SummonerModule' },
    { path: '**', component: NoContentComponent },
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            routes,
            {
                useHash: false
            }
        )
    ], exports: [
        RouterModule
    ], declarations: [
        NoContentComponent
    ]
})
export class AppRoutingModule { }
