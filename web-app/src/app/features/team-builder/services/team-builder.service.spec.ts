import { TestBed, inject } from '@angular/core/testing';

import { TeamBuilderService } from './team-builder.service';

describe('TeamBuilderService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TeamBuilderService]
    });
  });

  it('should be created', inject([TeamBuilderService], (service: TeamBuilderService) => {
    expect(service).toBeTruthy();
  }));
});
