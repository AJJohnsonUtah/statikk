import { Component, OnInit, Input } from '@angular/core';
import { StaticDataService } from '../../../core/services/static-data.service';
import { StaticChampion } from '../../models/riot-api-types/static-champion';

@Component({
  selector: 'app-champion-image',
  templateUrl: './champion-image.component.html',
  styleUrls: ['./champion-image.component.scss']
})
export class ChampionImageComponent implements OnInit {

  constructor(private staticDataService: StaticDataService) { }

  public staticChampions: Map<string, StaticChampion>;
  public version: string;

  @Input()
  public championId: string;

  ngOnInit() {
    this.staticDataService.getChampions().subscribe((staticChampions) => {
      this.staticChampions = staticChampions.data;
    });
    this.staticDataService.getRealmsData().subscribe((realmsData) => {
      this.version = realmsData.v;
    });
  }

}
