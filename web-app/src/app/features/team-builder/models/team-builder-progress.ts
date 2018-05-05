import { ChampionPick } from './champion-pick';

export class TeamBuilderProgress {
    summonerName: string;
    lane: string;
    allyChampionIds: number[];
    enemyChampionIds: number[];

    constructor(summonerName: string, lane: string, allyChampionIds: ChampionPick[], enemyChampionIds: ChampionPick[]) {
        this.summonerName = summonerName;
        this.lane = lane;
        this.allyChampionIds = allyChampionIds.filter((pick) => pick.championId != null).map((pick) => pick.championId);
        this.enemyChampionIds = enemyChampionIds.filter((pick) => pick.championId != null).map((pick) => pick.championId);
    }
}
