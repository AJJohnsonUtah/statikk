import { Component, OnInit } from '@angular/core';
import { StaticDataService } from './core/services/static-data.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private staticDataService: StaticDataService) {
  }

  public ngOnInit() {
    this.staticDataService.loadRealmsData();
  }
}
