import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Location } from '@angular/common';
import 'rxjs/add/operator/switchMap';
import { StaticChampionDetail } from '../../../shared/models/riot-api-types/static-champion-detail';
import { ChampionWinRate } from '../../../shared/models/statikk-api-types/ChampionWinRate';
import { StaticDataService } from '../../../core/services/static-data.service';
import { ChampionWinRateService } from '../../../core/services/champion-win-rate.service';

@Component({
    selector: 'app-champion-detail',
    styleUrls: ['./champion-detail.component.scss'],
    templateUrl: './champion-detail.component.html'
})
export class ChampionDetailComponent implements OnInit {
    public champion: StaticChampionDetail;
    public championWinRates: Map<string, ChampionWinRate>;

    public constructor(
        private route: ActivatedRoute,
        private location: Location,
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService
    ) { }

    public ngOnInit() {
        this.champion = new StaticChampionDetail();
        console.log('initiating champion detail component');
        this.route.params
            .subscribe((params: Params) =>
                this.loadChampionDetail(+params['id']));
        this.loadChampionWinRates();
    }

    public getSpellLetterFromIndex(index: number): string {
        switch (index) {
            case 0: return 'Q';
            case 1: return 'W';
            case 2: return 'E';
            case 3: return 'R';
            case 4: return 'Q';
            case 5: return 'W';
            case 6: return 'E';
            case 7: return 'R';
            default: return 'WTF';
        }
    }

    private loadChampionDetail(championId: number): void {
        this.staticDataService.getChampion(championId)
            .subscribe((championDetail: StaticChampionDetail) =>
                this.champion = championDetail);
    }

    private loadChampionWinRates(): void {
        this.championWinRateService
            .getAllChampionWinRates()
            .subscribe((championWinRateData: Map<string, ChampionWinRate>) => {
                this.championWinRates = championWinRateData;
            });
    }

}
