export class StaticChampion {
    public id: number;
    public key: string;
    public name: string;
    public title: string;
    public image: {
        full: string;
        sprite: string;
        group: string;
        x: number;
        y: number;
        w: number;
        h: number;
    };
    public spriteStyles: { [id: string]: any };
}
