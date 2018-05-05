import { TestBed, inject } from '@angular/core/testing';

import { ApiStatusService } from './api-status.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs/observable/of';
import { HttpCacheService } from './http-cache.service';

describe('ApiStatusService', () => {

  class MockHttpCacheService {
    get(url: string) { return of(['1.2', '3.4']); }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ApiStatusService, { provide: HttpCacheService, useClass: MockHttpCacheService }]
    });
  });

  it('should be created', inject([ApiStatusService], (service: ApiStatusService) => {
    expect(service).toBeTruthy();
  }));
});
