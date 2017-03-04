import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ChampionsComponent} from './champions/champions.component';
import {StatsComponent} from './stats/stats.component';

const routes: Routes = [
    {path: '', redirectTo: '/', pathMatch: 'full'},
    {path: 'champions', component: ChampionsComponent},
    {path: 'stats', component: StatsComponent}
];
@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}