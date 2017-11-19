import { BaseWinRate } from './base-win-rate';

export interface WinRateWithTotal<T extends BaseWinRate> {
    totalPlayed: number;
    winRateData: T[];
}
