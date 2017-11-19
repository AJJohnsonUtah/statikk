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
    public version: string;
    constructor(
        private staticDataService: StaticDataService,
        private championWinRateService: ChampionWinRateService,
        private router: Router,
        private formBuilder: FormBuilder
    ) { }

    public ngOnInit() {
        this.sortColumn = 'win-rate';
        this.reversed = true;
        this.filterCriteraFormGroup = this.formBuilder.group({
            matchType: '450'
        });
        this.staticDataService.getRealmsData().subscribe((realmsData: RealmDto) => {
            this.version = realmsData.v;
        });
        this.loadChampionWinRates();
        this.loadStaticChampions();
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
            .getAllChampionWinRates(this.filterCriteraFormGroup).subscribe((championWinRateData: WinRateWithTotal<ChampionWinRate>) => {
                this.championWinRates = championWinRateData.winRateData;
                this.matchesPlayed = championWinRateData.totalPlayed;
            });
    }

    private loadStaticChampions(): void {
        this.staticDataService
            .getChampions()
            .subscribe((staticChampionsData) => {
                this.staticChampions = staticChampionsData.data;
            });
    }

}
