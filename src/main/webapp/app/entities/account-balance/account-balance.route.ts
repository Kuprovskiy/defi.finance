import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from './account-balance.service';
import { AccountBalanceComponent } from './account-balance.component';
import { AccountBalanceDetailComponent } from './account-balance-detail.component';
import { AccountBalanceUpdateComponent } from './account-balance-update.component';
import { AccountBalanceDeletePopupComponent } from './account-balance-delete-dialog.component';
import { IAccountBalance } from 'app/shared/model/account-balance.model';

@Injectable({ providedIn: 'root' })
export class AccountBalanceResolve implements Resolve<IAccountBalance> {
  constructor(private service: AccountBalanceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountBalance> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountBalance>) => response.ok),
        map((accountBalance: HttpResponse<AccountBalance>) => accountBalance.body)
      );
    }
    return of(new AccountBalance());
  }
}

export const accountBalanceRoute: Routes = [
  {
    path: '',
    component: AccountBalanceComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AccountBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountBalanceDetailComponent,
    resolve: {
      accountBalance: AccountBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountBalanceUpdateComponent,
    resolve: {
      accountBalance: AccountBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountBalances'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountBalanceUpdateComponent,
    resolve: {
      accountBalance: AccountBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountBalances'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountBalancePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountBalanceDeletePopupComponent,
    resolve: {
      accountBalance: AccountBalanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AccountBalances'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
