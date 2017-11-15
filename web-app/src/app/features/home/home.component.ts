import {
    Component,
    OnInit
} from '@angular/core';

import { Router } from '@angular/router';
import { Subject } from 'rxjs/Subject';
import 'rxjs/add/operator/debounceTime';
import { SummonerDataService } from '../../core/services/summoner-data.service';

@Component({
    selector: 'app-home',
    styleUrls: ['./home.component.scss'],
    templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
    public summonerName: string;
    public lookupError: string;
    private lookupErrorSubject = new Subject<string>();
    constructor(
        private summonerDataService: SummonerDataService,
        private router: Router
    ) { }

    public ngOnInit() {
        this.summonerName = '';
        this.lookupErrorSubject.subscribe((message) => this.lookupError = message);
        this.lookupErrorSubject.debounceTime(5000).subscribe(() => this.lookupError = null);
    }

    public checkSummonerExists(): void {
        const cleanSummonerName = this.cleanSummonerName(this.summonerName);
        if (cleanSummonerName.length === 0) {
            this.lookupErrorSubject.next('Enter a summoner\'s name.');
            return;
        }
        this.summonerDataService.getSummonerData(cleanSummonerName)
            .subscribe(
            (data) => this.navigateToSummonerPage(),
            (errorMessage) =>
                this.lookupErrorSubject.next('Summoner (' + this.summonerName + ') not found')
            );
    }

    private navigateToSummonerPage(): void {
        this.router.navigate(['/summoner', this.cleanSummonerName(this.summonerName)]);
    }

    private cleanSummonerName(dirtySummonerName: string): string {
        return dirtySummonerName.toLocaleLowerCase().replace(/ /g, '');
    }

}
