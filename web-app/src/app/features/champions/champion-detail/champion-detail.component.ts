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
import { MatchupService } from '../../../core/services/matchup.service';
import { TeamupService } from '../../../core/services/teamup.service';
import { SignificantMatchups } from '../../../shared/models/statikk-api-types/significant-matchups';
import { SignificantTeamups } from '../../../shared/models/statikk-api-types/significant-teamups';
import { StaticChampion } from '../../../shared/models/riot-api-types/static-champion';

@Component({
    selector: 'app-champion-detail',
    styleUrls: ['./champion-detail.component.scss'],
    templateUrl: './champion-detail.component.html'
})
export class ChampionDetailComponent implements OnInit {
    public championId: number;
    public champion: StaticChampion;
    public matchTypeWinRates: Map<string, BaseWinRate>;
    public version: string;
    public matchups: SignificantMatchups;
    public teamups: SignificantTeamups;
    public staticChamps: Map<string, StaticChampion>;

    public constructor(
        private route: ActivatedRoute,
        private location: Location,
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private matchupService: MatchupService,
        private teamupService: TeamupService,
        private apiStatusService: ApiStatusService
    ) { }

    public ngOnInit() {
        this.apiStatusService.getVersions().subscribe((versions: string[]) => {
            this.version = versions[0] + '.1';
        });
        this.route.params
            .subscribe((params: Params) => {
                this.championId = +params['id'];
                this.loadChampionDetail();
                this.loadChampionWinRates();
                this.loadChampionMatchups();
                this.loadChampionTeamups();
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
        this.staticDataService.getChampions()
            .subscribe((championDetails: Map<string, StaticChampion>) => {
                this.champion = championDetails[this.championId];
                this.staticChamps = championDetails;
            });
    }

    private loadChampionWinRates(): void {
        this.championWinRateService
            .getChampionMatchTypeWinRates(this.championId, null)
            .subscribe((championWinRateData: Map<string, BaseWinRate>) => {
                this.matchTypeWinRates = championWinRateData;
            });
    }

    private loadChampionMatchups(): void {
        this.matchupService
            .getSignificantMatchups(this.championId)
            .subscribe((matchupData: SignificantMatchups) => {
                this.matchups = matchupData;
            });
    }

    private loadChampionTeamups(): void {
        this.teamupService
            .getSignificantTeamups(this.championId)
            .subscribe((teamupData: SignificantTeamups) => {
                this.teamups = teamupData;
            });
    }

}
