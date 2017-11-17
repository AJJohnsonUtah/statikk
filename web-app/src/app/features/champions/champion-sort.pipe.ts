import { Pipe, PipeTransform } from '@angular/core';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';
import { BaseWinRate } from '../../shared/models/statikk-api-types/base-win-rate';
import { ChampionWinRate } from '../../shared/models/statikk-api-types/champion-win-rate';

@Pipe({
    name: 'myChampionSort'
})

export class ChampionSortPipe implements PipeTransform {

    public transform(
        value: ChampionWinRate[],
        sortColumn: string,
        reversed: boolean,
        staticChampions: Map<string, StaticChampion>
    ): BaseWinRate[] {
        switch (sortColumn) {
            case 'win-rate': value.sort(reversed ? this.winRateSortReversed : this.winRateSort);
                break;
            case 'pick-rate': value.sort(reversed ? this.pickRateSortReversed : this.pickRateSort);
                break;
            case 'name': value.sort(reversed ? this.nameSortReversed(staticChampions) : this.nameSort(staticChampions));
                break;
            default: break;
        }
        return value;
    }

    private winRateSort(a: ChampionWinRate, b: ChampionWinRate): number {
        return a.winRate - b.winRate;
    }

    private winRateSortReversed(a: ChampionWinRate, b: ChampionWinRate): number {
        return b.winRate - a.winRate;
    }

    private pickRateSort(a: ChampionWinRate, b: ChampionWinRate): number {
        return a.playedCount - b.playedCount;
    }

    private pickRateSortReversed(a: ChampionWinRate, b: ChampionWinRate): number {
        return b.playedCount - a.playedCount;
    }

    private nameSort(staticChampions: Map<string, StaticChampion>): (a: ChampionWinRate, b: ChampionWinRate) => number {
        return (a: ChampionWinRate, b: ChampionWinRate): number => {
            return (staticChampions[a.championId].name > staticChampions[b.championId].name) ? 1 : -1;
        };
    }

    private nameSortReversed(staticChampions: Map<string, StaticChampion>): (a: ChampionWinRate, b: ChampionWinRate) => number {
        return (a: ChampionWinRate, b: ChampionWinRate): number => {
            return (staticChampions[a.championId].name < staticChampions[b.championId].name) ? 1 : -1;
        };
    }

}
