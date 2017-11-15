import { StaticChampion } from './static-champion';

export interface StaticChampionsData {
    data: Map<string, StaticChampion>;
    type: string;
    version: string;
}
