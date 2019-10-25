import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DefiSharedModule } from 'app/shared';
import {
  TrustedDeviceComponent,
  TrustedDeviceDetailComponent,
  TrustedDeviceUpdateComponent,
  TrustedDeviceDeletePopupComponent,
  TrustedDeviceDeleteDialogComponent,
  trustedDeviceRoute,
  trustedDevicePopupRoute
} from './';

const ENTITY_STATES = [...trustedDeviceRoute, ...trustedDevicePopupRoute];

@NgModule({
  imports: [DefiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TrustedDeviceComponent,
    TrustedDeviceDetailComponent,
    TrustedDeviceUpdateComponent,
    TrustedDeviceDeleteDialogComponent,
    TrustedDeviceDeletePopupComponent
  ],
  entryComponents: [
    TrustedDeviceComponent,
    TrustedDeviceUpdateComponent,
    TrustedDeviceDeleteDialogComponent,
    TrustedDeviceDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefiTrustedDeviceModule {}
