import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DefiSharedModule } from 'app/shared';
import {
  AccountBalanceComponent,
  AccountBalanceDetailComponent,
  AccountBalanceUpdateComponent,
  AccountBalanceDeletePopupComponent,
  AccountBalanceDeleteDialogComponent,
  accountBalanceRoute,
  accountBalancePopupRoute
} from './';

const ENTITY_STATES = [...accountBalanceRoute, ...accountBalancePopupRoute];

@NgModule({
  imports: [DefiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountBalanceComponent,
    AccountBalanceDetailComponent,
    AccountBalanceUpdateComponent,
    AccountBalanceDeleteDialogComponent,
    AccountBalanceDeletePopupComponent
  ],
  entryComponents: [
    AccountBalanceComponent,
    AccountBalanceUpdateComponent,
    AccountBalanceDeleteDialogComponent,
    AccountBalanceDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefiAccountBalanceModule {}
