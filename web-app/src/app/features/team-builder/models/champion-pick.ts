import { Lane } from '../../../shared/models/statikk-api-types/filter-criteria/lane';

export interface ChampionPick {
    championId: number;
    summonerName?: string;
    lane: Lane;
}
