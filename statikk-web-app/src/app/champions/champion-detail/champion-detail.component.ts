import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Location } from '@angular/common';
import 'rxjs/add/operator/switchMap';
import { StaticChampion } from '../../services/riot-api-types/static-champion';
import { StaticDataService } from '../../services/static-data.service';
import { StaticChampionDetail } from '../../services/riot-api-types/static-champion-detail';

@Component({
    selector: 'champion-detail',
    styleUrls: ['./champion-detail.component.css'],
    templateUrl: './champion-detail.component.html'
})
export class ChampionDetailComponent implements OnInit {
    public champion: StaticChampionDetail;
    public constructor(
        private route: ActivatedRoute,
        private location: Location,
        private staticDataService: StaticDataService
    ) { }

    public ngOnInit() {
        this.champion = new StaticChampionDetail();
        console.log('initiating champion detail component');
        this.route.params
            .subscribe((params: Params) =>
                this.loadChampionDetail(+params['id']));
    }

    private loadChampionDetail(championId: number): void {
        this.staticDataService.getChampion(championId)
            .then((championDetail: StaticChampionDetail) =>
                this.champion = championDetail);
    }

}
