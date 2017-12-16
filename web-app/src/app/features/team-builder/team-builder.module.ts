import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeamBuilderRoutingModule } from './team-builder-routing.module';
import { TeamBuilderComponent } from './team-builder.component';

@NgModule({
  imports: [
    CommonModule,
    TeamBuilderRoutingModule
  ],
  declarations: [TeamBuilderComponent]
})
export class TeamBuilderModule { }
