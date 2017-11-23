import { TestBed, inject } from '@angular/core/testing';

import { FilterCriteriaService } from './filter-criteria.service';

describe('FilterCriteriaService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FilterCriteriaService]
    });
  });

  it('should be created', inject([FilterCriteriaService], (service: FilterCriteriaService) => {
    expect(service).toBeTruthy();
  }));
});
