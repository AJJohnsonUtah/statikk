import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Location } from '@angular/common';
import 'rxjs/add/operator/switchMap';
import { StaticChampionDetail } from '../../../shared/models/riot-api-types/static-champion-detail';
import { ChampionWinRate } from '../../../shared/models/statikk-api-types/champion-win-rate';
import { StaticDataService } from '../../../core/services/static-data.service';
import { ChampionWinRateService } from '../../../core/services/champion-win-rate.service';
import { WinRateWithTotal } from '../../../shared/models/statikk-api-types/win-rate-with-total';
import { RealmDto } from '../../../shared/models/riot-api-types/realm-dto';
import { ApiStatusService } from '../../../core/services/api-status.service';
import { BaseWinRate } from '../../../shared/models/statikk-api-types/base-win-rate';

@Component({
    selector: 'app-champion-detail',
    styleUrls: ['./champion-detail.component.scss'],
    templateUrl: './champion-detail.component.html'
})
export class ChampionDetailComponent implements OnInit {
    public championId: number;
    public champion: StaticChampionDetail;
    public matchTypeWinRates: Map<string, BaseWinRate>;
    public version: string;
    public constructor(
        private route: ActivatedRoute,
        private location: Location,
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private apiStatusService: ApiStatusService
    ) { }

    public ngOnInit() {
        this.apiStatusService.getVersions().subscribe((versions: string[]) => {
            this.version = versions[0] + '.1';
        });
        this.champion = new StaticChampionDetail();
        console.log('initiating champion detail component');
        this.route.params
            .subscribe((params: Params) => {
                this.championId = +params['id'];
                this.loadChampionDetail();
                this.loadChampionWinRates();
            });
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

    private loadChampionDetail(): void {
        this.staticDataService.getChampion(this.championId)
            .subscribe((championDetail: StaticChampionDetail) =>
                this.champion = championDetail);
    }

    private loadChampionWinRates(): void {
        this.championWinRateService
            .getChampionMatchTypeWinRates(this.championId, null)
            .subscribe((championWinRateData: Map<string, BaseWinRate>) => {
                this.matchTypeWinRates = championWinRateData;
            });
    }

}
