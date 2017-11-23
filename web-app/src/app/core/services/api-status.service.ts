import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ApiStatusService extends HttpService {

  private apiStatusPath = this.apiRoot + '/status';

  public getVersions(): Observable<String[]> {
    return this.httpClient.get<String[]>(this.apiStatusPath + '/supported-versions');
  }

}
