export interface Rank {
    name: string;
    id: string;
}

export const rankList: Rank[] = [
    { name: 'Challenger', id: 'CHALLENGER' },
    { name: 'Master', id: 'MASTER' },
    { name: 'Diamond', id: 'DIAMOND' },
    { name: 'Platinum', id: 'PLATINUM' },
    { name: 'Gold', id: 'GOLD' },
    { name: 'Silver', id: 'SILVER' },
    { name: 'Bronze', id: 'BRONZE' }
];
