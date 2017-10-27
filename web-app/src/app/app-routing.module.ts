import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './features/home/home.component';
import { NoContentComponent } from './core/components/no-content/no-content.component';
import { ChampionsComponent } from './features/champions/champions.component';
import { ChampionDetailComponent } from './features/champions/champion-detail/champion-detail.component';
import { NgModule } from '@angular/core';

const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'champions', component: ChampionsComponent },
    { path: 'champion/:id', component: ChampionDetailComponent },
    { path: '**', component: NoContentComponent },
];

@NgModule({
    imports: [
        RouterModule.forRoot(
            routes,
            {
                enableTracing: true,
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