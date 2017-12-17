import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeamBuilderRoutingModule } from './team-builder-routing.module';
import { TeamBuilderComponent } from './team-builder.component';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    TeamBuilderRoutingModule,
    SharedModule
  ],
  declarations: [TeamBuilderComponent]
})
export class TeamBuilderModule { }
