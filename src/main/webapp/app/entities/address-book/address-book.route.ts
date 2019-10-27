import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AddressBook } from 'app/shared/model/address-book.model';
import { AddressBookService } from './address-book.service';
import { AddressBookComponent } from './address-book.component';
import { AddressBookDetailComponent } from './address-book-detail.component';
import { AddressBookUpdateComponent } from './address-book-update.component';
import { AddressBookDeletePopupComponent } from './address-book-delete-dialog.component';
import { IAddressBook } from 'app/shared/model/address-book.model';

@Injectable({ providedIn: 'root' })
export class AddressBookResolve implements Resolve<IAddressBook> {
  constructor(private service: AddressBookService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAddressBook> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AddressBook>) => response.ok),
        map((addressBook: HttpResponse<AddressBook>) => addressBook.body)
      );
    }
    return of(new AddressBook());
  }
}

export const addressBookRoute: Routes = [
  {
    path: '',
    component: AddressBookComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'AddressBooks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AddressBookDetailComponent,
    resolve: {
      addressBook: AddressBookResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AddressBooks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AddressBookUpdateComponent,
    resolve: {
      addressBook: AddressBookResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AddressBooks'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AddressBookUpdateComponent,
    resolve: {
      addressBook: AddressBookResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AddressBooks'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const addressBookPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AddressBookDeletePopupComponent,
    resolve: {
      addressBook: AddressBookResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'AddressBooks'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
