import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ApiStatusService extends HttpService {

  private apiStatusPath = this.apiRoot + '/status';

  /**
   * Returns a list of League of Legends versions (e.g., "7.20") that Statikk has analyzed
   */
  public getVersions(): Observable<String[]> {
    return this.httpCacheService.get<String[]>(this.apiStatusPath + '/supported-versions');
  }

}
