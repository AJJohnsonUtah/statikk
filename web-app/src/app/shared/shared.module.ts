import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ChampionImageComponent } from './components/champion-image/champion-image.component';
import { ChampionNameSortPipe } from './pipes/champion-name-sort.pipe';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  declarations: [ChampionImageComponent, ChampionNameSortPipe],
  exports: [
    ReactiveFormsModule, ChampionImageComponent, ChampionNameSortPipe
  ]
})
export class SharedModule { }
