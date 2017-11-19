import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export abstract class HttpService {

    protected apiRoot = 'http://' + window.location.hostname + ':8080/api';

    constructor(protected httpClient: HttpClient) {

    }
}
