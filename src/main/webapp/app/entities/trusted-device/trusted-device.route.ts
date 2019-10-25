import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TrustedDevice } from 'app/shared/model/trusted-device.model';
import { TrustedDeviceService } from './trusted-device.service';
import { TrustedDeviceComponent } from './trusted-device.component';
import { TrustedDeviceDetailComponent } from './trusted-device-detail.component';
import { TrustedDeviceUpdateComponent } from './trusted-device-update.component';
import { TrustedDeviceDeletePopupComponent } from './trusted-device-delete-dialog.component';
import { ITrustedDevice } from 'app/shared/model/trusted-device.model';

@Injectable({ providedIn: 'root' })
export class TrustedDeviceResolve implements Resolve<ITrustedDevice> {
  constructor(private service: TrustedDeviceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITrustedDevice> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TrustedDevice>) => response.ok),
        map((trustedDevice: HttpResponse<TrustedDevice>) => trustedDevice.body)
      );
    }
    return of(new TrustedDevice());
  }
}

export const trustedDeviceRoute: Routes = [
  {
    path: '',
    component: TrustedDeviceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'TrustedDevices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrustedDeviceDetailComponent,
    resolve: {
      trustedDevice: TrustedDeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TrustedDevices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrustedDeviceUpdateComponent,
    resolve: {
      trustedDevice: TrustedDeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TrustedDevices'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrustedDeviceUpdateComponent,
    resolve: {
      trustedDevice: TrustedDeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TrustedDevices'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const trustedDevicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TrustedDeviceDeletePopupComponent,
    resolve: {
      trustedDevice: TrustedDeviceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'TrustedDevices'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
