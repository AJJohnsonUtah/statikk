import { Routes, RouterModule } from '@angular/router';
import { NoContentComponent } from './core/components/no-content/no-content.component';
import { ChampionsComponent } from './features/champions/champions.component';
import { ChampionDetailComponent } from './features/champions/champion-detail/champion-detail.component';
import { NgModule } from '@angular/core';
import { HomeComponent } from './features/home/home.component';
import { AboutComponent } from './features/about/about.component';

const routes: Routes = [
    { path: '', redirectTo: '/team-builder', pathMatch: 'full' },
    { path: 'about', component: AboutComponent },
    { path: 'champions', loadChildren: 'app/features/champions/champions.module#ChampionsModule' },
    { path: 'summoner', loadChildren: 'app/features/summoner/summoner.module#SummonerModule' },
    { path: 'team-builder', loadChildren: 'app/features/team-builder/team-builder.module#TeamBuilderModule' },
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
