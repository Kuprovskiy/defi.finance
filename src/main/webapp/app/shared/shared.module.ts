import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DefiSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [DefiSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [DefiSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefiSharedModule {
  static forRoot() {
    return {
      ngModule: DefiSharedModule
    };
  }
}
