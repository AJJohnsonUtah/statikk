import { Pipe, PipeTransform } from '@angular/core';
import { ChampionWinRate } from '../../../shared/models/statikk-api-types/champion-win-rate';
import { BaseWinRate } from '../../../shared/models/statikk-api-types/base-win-rate';
import { StaticChampion } from '../../../shared/models/riot-api-types/static-champion';

@Pipe({
  name: 'championSearch'
})
export class ChampionSearchPipe implements PipeTransform {

  public transform(
    championIds: number[],
    staticChampions: Map<string, StaticChampion>,
    searchText: string
  ): number[] {
    return championIds.filter((champId) => {
      const staticChampion: StaticChampion = staticChampions[champId.toString()];
      return staticChampion.name.toUpperCase().includes(searchText.toUpperCase());
    }).sort((champIdA, champIdB) => {
      const staticChampionA: StaticChampion = staticChampions[champIdA.toString()];
      const staticChampionB: StaticChampion = staticChampions[champIdB.toString()];
      return staticChampionA.name.localeCompare(staticChampionB.name);
    });
  }
}
