import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAddressBook } from 'app/shared/model/address-book.model';

type EntityResponseType = HttpResponse<IAddressBook>;
type EntityArrayResponseType = HttpResponse<IAddressBook[]>;

@Injectable({ providedIn: 'root' })
export class AddressBookService {
  public resourceUrl = SERVER_API_URL + 'api/address-books';

  constructor(protected http: HttpClient) {}

  create(addressBook: IAddressBook): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addressBook);
    return this.http
      .post<IAddressBook>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(addressBook: IAddressBook): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(addressBook);
    return this.http
      .put<IAddressBook>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAddressBook>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAddressBook[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(addressBook: IAddressBook): IAddressBook {
    const copy: IAddressBook = Object.assign({}, addressBook, {
      invitedDate: addressBook.invitedDate != null && addressBook.invitedDate.isValid() ? addressBook.invitedDate.toJSON() : null,
      createdDate: addressBook.createdDate != null && addressBook.createdDate.isValid() ? addressBook.createdDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.invitedDate = res.body.invitedDate != null ? moment(res.body.invitedDate) : null;
      res.body.createdDate = res.body.createdDate != null ? moment(res.body.createdDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((addressBook: IAddressBook) => {
        addressBook.invitedDate = addressBook.invitedDate != null ? moment(addressBook.invitedDate) : null;
        addressBook.createdDate = addressBook.createdDate != null ? moment(addressBook.createdDate) : null;
      });
    }
    return res;
  }
}
