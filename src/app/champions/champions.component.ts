import { Component, OnInit } from '@angular/core';
import { StaticChampion } from '../services/riot-api-types/static-champion';
import { StaticDataService } from '../services/static-data.service';
import { Router } from '@angular/router';
import { ChampionWinRate } from '../services/loltheory-api-types/championWinRate';
import { ChampionWinRateService } from '../services/champion-win-rate.service';

@Component({
    selector: 'champions',
    styleUrls: ['./champions.component.css'],
    templateUrl: './champions.component.html'
})

export class ChampionsComponent implements OnInit {
    public staticChampions: StaticChampion[];
    public championWinRates: Map<string, ChampionWinRate>;
    constructor(
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private router: Router,
    ) { }

    public ngOnInit() {
        this.staticChampions = [];
        this.loadChampionWinRates();
    }

    private loadChampionWinRates(): void {
        this.championWinRateService
            .getAllChampionWinRates()
            .then((championWinRateData) => {
                this.championWinRates = championWinRateData;
                this.loadStaticChampions();
            });
    }

    private loadStaticChampions(): void {
        this.staticDataService
            .getChampions()
            .then((staticChampionsData) => {
                this.staticChampions = this.convertStaticChampionsToArray(staticChampionsData);
            });
    }

    private convertStaticChampionsToArray(
        staticChampionsData: Map<string, StaticChampion>): StaticChampion[] {
        let tempArr: StaticChampion[] = [];
        Object.keys(staticChampionsData).forEach((key) => {
            let champ: StaticChampion = staticChampionsData[key];
            champ.spriteStyles = {
                'background': 'url(\'http://ddragon.leagueoflegends.com/cdn/7.5.2/img/sprite/' +
                champ.image.sprite + '\')',
                'background-position': -champ.image.x + 'px ' + -champ.image.y + 'px',
                'height': champ.image.h,
                'width': champ.image.w
            };
            tempArr.push(champ);
        });
        tempArr.sort((s1: StaticChampion, s2: StaticChampion) => s1.name.localeCompare(s2.name));
        return tempArr;
    }
}
