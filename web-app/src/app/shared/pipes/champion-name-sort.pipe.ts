import { Pipe, PipeTransform } from '@angular/core';
import { StaticChampion } from '../models/riot-api-types/static-champion';

@Pipe({
  name: 'championNameSort'
})
export class ChampionNameSortPipe implements PipeTransform {

  public transform(
    value: number[],
    reversed: boolean,
    staticChampions: Map<string, StaticChampion>
  ): number[] {
    value.sort(reversed ? this.nameSortReversed(staticChampions) : this.nameSort(staticChampions));

    return value;
  }

  private nameSort(staticChampions: Map<string, StaticChampion>): (a: number, b: number) => number {
    return (a: number, b: number): number => {
      return (staticChampions[a].name > staticChampions[b].name) ? 1 : -1;
    };
  }

  private nameSortReversed(staticChampions: Map<string, StaticChampion>): (a: number, b: number) => number {
    return (a: number, b: number): number => {
      return (staticChampions[a].name < staticChampions[b].name) ? 1 : -1;
    };
  }
}
