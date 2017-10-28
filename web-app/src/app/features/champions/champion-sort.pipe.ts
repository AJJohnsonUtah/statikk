import { Pipe, PipeTransform } from '@angular/core';
import { StaticChampion } from '../../shared/models/riot-api-types/static-champion';

@Pipe({
    name: 'myChampionSort'
})

export class ChampionSortPipe implements PipeTransform {
    public transform(
        value: StaticChampion[],
        sortColumn: string,
        reversed: boolean
    ): StaticChampion[] {
        switch (sortColumn) {
            case 'win-rate': value.sort(reversed ? this.winRateSortReversed : this.winRateSort);
                break;
            case 'pick-rate': value.sort(reversed ? this.pickRateSortReversed : this.pickRateSort);
                break;
            case 'name': value.sort(reversed ? this.nameSortReversed : this.nameSort);
                break;
            default: break;
        }
        return value;
    }

    private winRateSort(a: StaticChampion, b: StaticChampion): number {
        return a.winRate - b.winRate;
    }

    private winRateSortReversed(a: StaticChampion, b: StaticChampion): number {
        return b.winRate - a.winRate;
    }

    private pickRateSort(a: StaticChampion, b: StaticChampion): number {
        return a.pickRate - b.pickRate;
    }

    private pickRateSortReversed(a: StaticChampion, b: StaticChampion): number {
        return b.pickRate - a.pickRate;
    }

    private nameSort(a: StaticChampion, b: StaticChampion): number {
        return (a.name > b.name) ? 1 : -1;
    }

    private nameSortReversed(a: StaticChampion, b: StaticChampion): number {
        return (a.name < b.name) ? 1 : -1;
    }
}
