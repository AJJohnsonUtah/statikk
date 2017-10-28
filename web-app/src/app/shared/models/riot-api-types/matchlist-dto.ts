import { MatchReferenceDto } from './match-reference-dto';

export class MatchlistDto {
    public matches: MatchReferenceDto[];
    public totalGames: number;
    public startIndex: number;
    public endIndex: number;
}
