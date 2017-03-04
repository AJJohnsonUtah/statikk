import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {MainAppComponent} from './main-app/main-app.component';
import {NavbarComponent} from './navbar/navbar.component';
import {ChampionsComponent} from './champions/champions.component';
import {StatsComponent} from './stats/stats.component';
import {AppRoutingModule} from './app-routing.module';
import {HttpModule} from '@angular/http';
import {StaticDataService} from './services/static-data.service'

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpModule
    ],
    declarations: [MainAppComponent, NavbarComponent, ChampionsComponent, StatsComponent],
    providers: [
        StaticDataService
    ],
    bootstrap: [MainAppComponent]
})
export class AppModule {}