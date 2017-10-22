import { StaticChampion } from './static-champion';
import { SkinDto } from './skin-dto';

export class StaticChampionDetail extends StaticChampion {
    public allytips: string[];
    public blurb: string;
    public enemytips: string[];
    public info: {

    };
    public lore: string;
    public partype: string;
    public passive: {
        id: string;
    };
    public skins: SkinDto[];
    public stats: {
        armor: number;
        armorperlevel: number;
        attackdamage: number;
        attackdamageperlevel: number;
        attackrange: number;
        attackspeedoffset: number;
        attackspeedperlevel: number;
        crit: number;
        critperlevel: number;
        hp: number;
        hpperlevel: number;
        hpregen: number;
        hpregenperlevel: number;
        movespeed: number;
        mp: number;
        mpperlevel: number;
        mpregen: number;
        mpregenperlevel: number;
        spellblock: number;
        spellblockperlevel: number;
    };
    public tags: string[];
}
