import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/share';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class HttpCacheService {

  constructor(private httpClient: HttpClient) { }

  // Stores Observables for specific URL strings;
  // Used for requests that are currently in flight
  private staticDataObservables: Map<string, Observable<any>> = new Map<string, Observable<any>>();

  // Stores HTTP response data for specific URL strings;
  // Used for requests that have completed
  private staticDataResponses: Map<string, any> = new Map<string, any>();

  /**
   * Retrieve the previously cached response of the HTTP GET request for the specified URL,
   * if no such cached response exists, then the HTTP call is performed and cached
   *
   * @param url: The complete URL string to perform an HTTP GET request
   */
  public get<Type>(url: string): Observable<Type> {
    if (this.staticDataResponses[url]) {
      // If cached completed response for URL, return it
      return of(this.staticDataResponses[url] as Type);
    } else if (this.staticDataObservables[url]) {
      // If existing request for URL still in flight, return it 
      return this.staticDataObservables[url];
    } else {
      // If no previous request has been cached, perform a new one
      this.staticDataObservables[url] = this.httpClient.get<Type>(url)
        .map((response) => {
          this.staticDataObservables[url] = null;
          this.staticDataResponses[url] = response;
          return response as Type;
        }).share();
    }
    return this.staticDataObservables[url];
  }

}
