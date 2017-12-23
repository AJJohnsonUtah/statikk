import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ChampionImageComponent } from './components/champion-image/champion-image.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  declarations: [ChampionImageComponent],
  exports: [
    ReactiveFormsModule, ChampionImageComponent
  ]
})
export class SharedModule { }
