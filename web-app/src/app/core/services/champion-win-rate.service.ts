import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/toPromise';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/champion-win-rate';
import { HttpService } from './http.service';
import { WinRateWithTotal } from '../../shared/models/statikk-api-types/win-rate-with-total';
import { FormGroup } from '@angular/forms/src/model';


@Injectable()
export class ChampionWinRateService extends HttpService {

    public getAllChampionWinRates(criteriaFormGroup: FormGroup): Observable<WinRateWithTotal<ChampionWinRate>> {
        const allWinRatesUrl: string = this.apiRoot + '/champion/win-rate/all';
        const params: URLSearchParams = new URLSearchParams();
        return this.httpClient.get<WinRateWithTotal<ChampionWinRate>>(allWinRatesUrl);
    }
}
