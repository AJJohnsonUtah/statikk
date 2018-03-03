import { TestBed, inject } from '@angular/core/testing';

import { MatchupService } from './Matchup.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpCacheService } from './http-cache.service';

class MockHttpCacheService {
}

describe('MatchupServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [MatchupService, { provide: HttpCacheService, useClass: MockHttpCacheService }]
    });
  });

  it('should be created', inject([MatchupService], (service: MatchupService) => {
    expect(service).toBeTruthy();
  }));
});
