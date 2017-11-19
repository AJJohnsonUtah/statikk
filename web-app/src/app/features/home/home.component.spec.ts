import { NO_ERRORS_SCHEMA } from '@angular/core';
import {
    inject,
    async,
    TestBed,
    ComponentFixture,
    tick
} from '@angular/core/testing';
import { Component } from '@angular/core';
import {
    BaseRequestOptions,
    ConnectionBackend,
    ResponseOptions
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

// Load the implementations that should be tested
import { HomeComponent } from './home.component';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SummonerDataService } from '../../core/services/summoner-data.service';
import { SummonerDto } from '../../shared/models/riot-api-types/summoner-dto';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

describe(`Home`, () => {
    let comp: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;
    const validSummonerData: SummonerDto = {
        id: 27673684,
        accountId: 42264797,
        name: 'GrannysCookies',
        profileIconId: 774,
        revisionDate: 1495236775000,
        summonerLevel: 30
    };
    const summonerNotFoundData = {
        httpStatus: 'NOT_FOUND',
        status: 'Error',
        message: 'Summoner grannyscoOkiesaaaa not found'
    };
    const mockRouter = {
        navigate: jasmine.createSpy('navigate')
    };

    class SummonerDataServiceStub {
        getSummonerData(summonerName: string): Observable<SummonerDto> {
            if (summonerName.startsWith('invalid')) {
                return Observable.throw(summonerNotFoundData);
            }
            return Observable.of(validSummonerData);
        }
    }


    // async beforeEach
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [FormsModule],
            declarations: [HomeComponent],
            providers: [
                { provide: SummonerDataService, useClass: SummonerDataServiceStub },
                { provide: Router, useValue: mockRouter },
            ]
        })
            .compileComponents(); // compile template and css
    }));

    // synchronous beforeEach
    beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;
        fixture = TestBed.createComponent(HomeComponent);
        comp = fixture.componentInstance;
        mockRouter.navigate.calls.reset();
        fixture.detectChanges(); // trigger initial data binding
    });

    it('should default to an empty summoner name', () => {
        comp.ngOnInit();
        expect(comp.summonerName).toEqual('');
    });

    it('should fetch summoner data and navigate to summoner page', async(() => {
        comp.summonerName = 'TestUser';
        comp.checkSummonerExists();
        fixture.whenStable().then(() => {
            expect(mockRouter.navigate).toHaveBeenCalledWith(['/summoner', comp.summonerName.toLowerCase()]);
        });
    }));

    it('should fetch invalid summoner data and not navigate to summoner page', async(() => {
        comp.summonerName = 'invalidGrannysCookies';
        comp.checkSummonerExists();
        fixture.whenStable().then(() => { // wait for async getQuote
            expect(mockRouter.navigate.calls.count()).toBe(0, 'navigate called 0 times');
        });

    }));
});
