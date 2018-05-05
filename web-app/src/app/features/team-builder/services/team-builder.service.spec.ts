import { TestBed, inject } from '@angular/core/testing';

import { TeamBuilderService } from './team-builder.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpCacheService } from '../../../core/services/http-cache.service';

describe('TeamBuilderService', () => {

  class MockHttpCacheService {
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeamBuilderService, { provide: HttpCacheService, useClass: MockHttpCacheService }]
    });
  });

  it('should be created', inject([TeamBuilderService], (service: TeamBuilderService) => {
    expect(service).toBeTruthy();
  }));
});
