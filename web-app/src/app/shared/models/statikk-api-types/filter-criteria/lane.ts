export interface Lane {
    name: string;
    id: string;
}

export const laneList: Lane[] = [
    { name: 'Top', id: 'TOP' },
    { name: 'Jungle', id: 'JUNGLE' },
    { name: 'Middle', id: 'MIDDLE' },
    { name: 'Bottom', id: 'BOTTOM' },
    { name: 'Support', id: 'SUPPORT' }
];
