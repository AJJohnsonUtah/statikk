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
    Http,
    ResponseOptions
} from '@angular/http';
import { MockBackend } from '@angular/http/testing';

// Load the implementations that should be tested
import { HomeComponent } from './home.component';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { SummonerDataService } from '../../core/services/summoner-data.service';

describe(`Home`, () => {
    let comp: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;
    let summonerDataService: SummonerDataService;
    const validSummonerData = {
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
    let summonerServiceSpy: jasmine.Spy;

    // async beforeEach
    beforeEach(async(() => {
        TestBed.configureTestingModule({
            imports: [FormsModule],
            declarations: [HomeComponent],
            providers: [
                SummonerDataService,
                { provide: Router, useValue: mockRouter },
                MockBackend,
                BaseRequestOptions,
                {
                    provide: Http,
                    useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                        return new Http(backendInstance, defaultOptions);
                    },
                    deps: [MockBackend, BaseRequestOptions]
                },
            ]
        })
            .compileComponents(); // compile template and css
    }));

    // synchronous beforeEach
    beforeEach(() => {
        jasmine.DEFAULT_TIMEOUT_INTERVAL = 10000;
        fixture = TestBed.createComponent(HomeComponent);
        comp = fixture.componentInstance;

        summonerDataService = fixture.debugElement.injector.get(SummonerDataService);
        summonerServiceSpy = spyOn(summonerDataService, 'getSummonerData');
        mockRouter.navigate.calls.reset();

        fixture.detectChanges(); // trigger initial data binding
    });

    it('should default to an empty summoner name', () => {
        comp.ngOnInit();
        expect(comp.summonerName).toEqual('');
    });

    it('should fetch summoner data and navigate to summoner page', async(() => {
        comp.summonerName = 'GrannysCookies';

        summonerServiceSpy.and.returnValue(Promise.resolve(validSummonerData));

        comp.checkSummonerExists();

        fixture.whenStable().then(() => { // wait for async getQuote
            expect(summonerServiceSpy).toHaveBeenCalled();
            expect(mockRouter.navigate).toHaveBeenCalledWith(['/summoner', 'grannyscookies']);

        });

    }));

    it('should fetch invalid summoner data and not navigate to summoner page', async(() => {
        comp.summonerName = 'GrannysCookiesAAAA';

        summonerServiceSpy.and.returnValue(Promise.reject(summonerNotFoundData));

        comp.checkSummonerExists();

        fixture.whenStable().then(() => { // wait for async getQuote
            expect(summonerServiceSpy).toHaveBeenCalled();
            expect(mockRouter.navigate.calls.count()).toBe(0, 'navigate called 0 times');
        });

    }));
});
