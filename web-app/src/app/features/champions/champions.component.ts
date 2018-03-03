import { Component, OnInit, Pipe } from '@angular/core';
import { Router } from '@angular/router';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/champion-win-rate';
import { StaticDataService } from '../../core/services/static-data.service';
import { ChampionWinRateService } from '../../core/services/champion-win-rate.service';
import { Observable } from 'rxjs/Observable';
import { WinRateWithTotal } from '../../shared/models/statikk-api-types/win-rate-with-total';
import { FormBuilder, FormGroup } from '@angular/forms';
import { RealmDto } from '../../shared/models/riot-api-types/realm-dto';
import { laneList, Lane } from '../../shared/models/statikk-api-types/filter-criteria/lane';
import { Rank, rankList } from '../../shared/models/statikk-api-types/filter-criteria/rank';
import { ApiStatusService } from '../../core/services/api-status.service';
import { FilterCriteriaGroup } from '../../shared/models/statikk-api-types/filter-criteria/filter-criteria-group';

@Component({
    selector: 'app-champions',
    styleUrls: ['./champions.component.scss'],
    templateUrl: './champions.component.html',
})

export class ChampionsComponent implements OnInit {
    public staticChampions: Map<string, StaticChampion>;
    public championWinRates: ChampionWinRate[];
    public matchesPlayed: number;
    public sortColumn: string;
    public reversed: boolean;
    public filterCriteraFormGroup: FormGroup;
    public versions: string[];
    public lanes: Lane[];
    public ranks: Rank[];

    constructor(
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private apiStatusService: ApiStatusService,
        private router: Router,
        private formBuilder: FormBuilder
    ) { }

    public ngOnInit() {
        this.lanes = laneList;
        this.ranks = rankList;
        this.sortColumn = 'win-rate';
        this.reversed = true;
        this.filterCriteraFormGroup = this.formBuilder.group({
            matchType: '420',
            rank: '',
            lane: '',
            version: ''
        });
        this.apiStatusService.getVersions().subscribe((versions: string[]) => {
            this.versions = versions;
            this.filterCriteraFormGroup.controls.version.setValue(this.versions[0]);
            this.loadChampionWinRates();
        });
        this.loadStaticChampions();
    }

    public chooseSort(selectedSortColumn: string) {
        if (this.sortColumn === selectedSortColumn) {
            this.reversed = !this.reversed;
        } else {
            this.sortColumn = selectedSortColumn;
        }
    }

    public loadChampionWinRates(): void {

        this.championWinRateService
            .getAllChampionWinRates(this.filterCriteraFormGroup.value as FilterCriteriaGroup)
            .subscribe((championWinRateData: WinRateWithTotal<ChampionWinRate>) => {
                this.championWinRates = championWinRateData.winRateData;
                this.matchesPlayed = championWinRateData.totalPlayed;
            });
    }

    private loadStaticChampions(): void {
        this.staticDataService
            .getChampions()
            .subscribe((staticChampionsData) => {
                this.staticChampions = staticChampionsData;
            });
    }

}
