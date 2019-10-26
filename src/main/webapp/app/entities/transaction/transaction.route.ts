import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { TransactionComponent } from './transaction.component';
import { TransactionDetailComponent } from './transaction-detail.component';
import { TransactionUpdateComponent } from './transaction-update.component';
import { TransactionDeletePopupComponent } from './transaction-delete-dialog.component';
import { ITransaction } from 'app/shared/model/transaction.model';

@Injectable({ providedIn: 'root' })
export class TransactionResolve implements Resolve<ITransaction> {
  constructor(private service: TransactionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransaction> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Transaction>) => response.ok),
        map((transaction: HttpResponse<Transaction>) => transaction.body)
      );
    }
    return of(new Transaction());
  }
}

export const transactionRoute: Routes = [
  {
    path: '',
    component: TransactionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionDetailComponent,
    resolve: {
      transaction: TransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionUpdateComponent,
    resolve: {
      transaction: TransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionUpdateComponent,
    resolve: {
      transaction: TransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionDeletePopupComponent,
    resolve: {
      transaction: TransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Transactions'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
