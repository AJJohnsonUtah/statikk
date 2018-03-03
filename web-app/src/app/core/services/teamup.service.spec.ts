import { TestBed, inject } from '@angular/core/testing';

import { TeamupService } from './teamup.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpCacheService } from './http-cache.service';

class MockHttpCacheService {
}

describe('TeamupServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [TeamupService, { provide: HttpCacheService, useClass: MockHttpCacheService }]
    });
  });

  it('should be created', inject([TeamupService], (service: TeamupService) => {
    expect(service).toBeTruthy();
  }));
});
