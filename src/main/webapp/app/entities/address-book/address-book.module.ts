import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DefiSharedModule } from 'app/shared';
import {
  AddressBookComponent,
  AddressBookDetailComponent,
  AddressBookUpdateComponent,
  AddressBookDeletePopupComponent,
  AddressBookDeleteDialogComponent,
  addressBookRoute,
  addressBookPopupRoute
} from './';

const ENTITY_STATES = [...addressBookRoute, ...addressBookPopupRoute];

@NgModule({
  imports: [DefiSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AddressBookComponent,
    AddressBookDetailComponent,
    AddressBookUpdateComponent,
    AddressBookDeleteDialogComponent,
    AddressBookDeletePopupComponent
  ],
  entryComponents: [AddressBookComponent, AddressBookUpdateComponent, AddressBookDeleteDialogComponent, AddressBookDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefiAddressBookModule {}
