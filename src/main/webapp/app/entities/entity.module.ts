import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'asset',
        loadChildren: () => import('./asset/asset.module').then(m => m.DefiAssetModule)
      },
      {
        path: 'trusted-device',
        loadChildren: () => import('./trusted-device/trusted-device.module').then(m => m.DefiTrustedDeviceModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DefiEntityModule {}
