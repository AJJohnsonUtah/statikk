import { TestBed, inject } from '@angular/core/testing';

import { HttpCacheService } from './http-cache.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('HttpCacheService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [HttpCacheService],
      imports: [HttpClientTestingModule]
    });
  });

  it('should be created', inject([HttpCacheService], (service: HttpCacheService) => {
    expect(service).toBeTruthy();
  }));
});
