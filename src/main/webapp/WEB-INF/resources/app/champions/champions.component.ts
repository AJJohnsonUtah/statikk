import {Component, OnInit} from '@angular/core';
import {StaticChampion} from '../services/api-types/static-champion';
import {StaticDataService} from '../services/static-data.service';
import {Router} from '@angular/router';

@Component({
    moduleId: module.id,
    selector: 'champions',
    templateUrl: './champions.component.html',
    styleUrls: ['./champions.component.css']
})

export class ChampionsComponent implements OnInit {    
    staticChampions: StaticChampion[];
    constructor(
        private staticDataService: StaticDataService,
        private router: Router
    ) {}
    
    ngOnInit(): void {
        this.getStaticChampions();
    }
    
    getStaticChampions(): void {
        this.staticDataService.getChampions().then(staticChampions => this.staticChampions = staticChampions);
    }
    
}