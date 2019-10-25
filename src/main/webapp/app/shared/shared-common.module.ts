import { NgModule } from '@angular/core';

import { DefiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
  imports: [DefiSharedLibsModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent],
  exports: [DefiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class DefiSharedCommonModule {}
