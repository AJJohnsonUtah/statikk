import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/champion-win-rate';
import { HttpService } from './http.service';
import { WinRateWithTotal } from '../../shared/models/statikk-api-types/win-rate-with-total';
import { FormGroup } from '@angular/forms/src/model';


@Injectable()
export class ChampionWinRateService extends HttpService {

    public getAllChampionWinRates(criteriaFormGroup: FormGroup): Observable<WinRateWithTotal<ChampionWinRate>> {
        const allWinRatesUrl: string = this.apiRoot + '/champion/win-rate/all';
        let params: HttpParams = new HttpParams();
        for (const criteria in criteriaFormGroup.value) {
            if (criteriaFormGroup.value.hasOwnProperty(criteria)) {
                params = params.append(criteria, criteriaFormGroup.value[criteria]);
            }
        }
        return this.httpClient.get<WinRateWithTotal<ChampionWinRate>>(allWinRatesUrl, { params: params });
    }
}
