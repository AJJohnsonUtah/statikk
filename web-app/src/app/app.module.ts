import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from "./app-routing.module";
import { ChampionsComponent } from "./features/champions/champions.component";
import { HomeComponent } from "./features/home/home.component";
import { ChampionDetailComponent } from "./features/champions/champion-detail/champion-detail.component";
import { StaticDataService } from "./services/static-data.service";
import { ChampionWinRateService } from "./services/champion-win-rate.service";
import { Http } from "@angular/http";

@NgModule({
  declarations: [
    AppComponent, HomeComponent, ChampionsComponent, ChampionDetailComponent
  ],
  imports: [
    BrowserModule, AppRoutingModule, FormsModule
  ],
  providers: [StaticDataService, ChampionWinRateService, Http],
  bootstrap: [AppComponent]
})
export class AppModule { }
