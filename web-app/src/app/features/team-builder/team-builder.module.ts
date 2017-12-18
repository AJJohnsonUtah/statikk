import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeamBuilderRoutingModule } from './team-builder-routing.module';
import { TeamBuilderComponent } from './team-builder.component';
import { SharedModule } from '../../shared/shared.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    NgbModule,
    TeamBuilderRoutingModule,
    SharedModule
  ],
  declarations: [TeamBuilderComponent]
})
export class TeamBuilderModule { }
