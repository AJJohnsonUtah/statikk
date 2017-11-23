import { TestBed, inject } from '@angular/core/testing';

import { ApiStatusService } from './api-status.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('ApiStatusService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ApiStatusService]
    });
  });

  it('should be created', inject([ApiStatusService], (service: ApiStatusService) => {
    expect(service).toBeTruthy();
  }));
});
