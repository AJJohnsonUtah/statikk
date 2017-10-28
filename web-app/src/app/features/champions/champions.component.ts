import { Component, OnInit, Pipe } from '@angular/core';
import { Router } from '@angular/router';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/ChampionWinRate';
import { StaticDataService } from '../../shared/services/static-data.service';
import { ChampionWinRateService } from '../../shared/services/champion-win-rate.service';
@Component({
    selector: 'app-champions',
    styleUrls: ['./champions.component.scss'],
    templateUrl: './champions.component.html',
})

export class ChampionsComponent implements OnInit {
    public staticChampions: StaticChampion[];
    public championWinRates: Map<string, ChampionWinRate>;
    public matchesPlayed: number;
    public sortColumn: string;
    public reversed: boolean;
    constructor(
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private router: Router,
    ) { }

    public ngOnInit() {
        this.staticChampions = [];
        this.sortColumn = 'win-rate';
        this.reversed = true;
        this.loadChampionWinRates();
        this.matchesPlayed = 0;
    }

    public chooseSort(selectedSortColumn: string) {
        if (this.sortColumn === selectedSortColumn) {
            this.reversed = !this.reversed;
        } else {
            this.sortColumn = selectedSortColumn;
        }
    }

    private loadChampionWinRates(): void {
        this.championWinRateService
            .getAllChampionWinRates()
            .then((championWinRateData: Map<string, ChampionWinRate>) => {
                this.championWinRates = championWinRateData;
                Object.keys(this.championWinRates).forEach((champId) =>
                    this.matchesPlayed += this.championWinRates[champId].playedCount
                );
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
        const tempArr: StaticChampion[] = [];
        Object.keys(this.championWinRates).forEach((champId) => {
            const winRate = this.championWinRates[champId];
            staticChampionsData[winRate.championId].winRate =
                winRate.winCount / winRate.playedCount;
            staticChampionsData[winRate.championId].pickRate =
                winRate.playedCount / this.matchesPlayed;
        });
        Object.keys(staticChampionsData).forEach((key) => {
            const champ: StaticChampion = staticChampionsData[key];
            tempArr.push(champ);
        });
        return tempArr;
    }

    private navigateToChampion(championId: number) {
        this.router.navigate(['/champions', championId]);
    }
}
