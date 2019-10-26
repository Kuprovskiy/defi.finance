import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAccountBalance } from 'app/shared/model/account-balance.model';

type EntityResponseType = HttpResponse<IAccountBalance>;
type EntityArrayResponseType = HttpResponse<IAccountBalance[]>;

@Injectable({ providedIn: 'root' })
export class AccountBalanceService {
  public resourceUrl = SERVER_API_URL + 'api/account-balances';

  constructor(protected http: HttpClient) {}

  create(accountBalance: IAccountBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountBalance);
    return this.http
      .post<IAccountBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(accountBalance: IAccountBalance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(accountBalance);
    return this.http
      .put<IAccountBalance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAccountBalance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAccountBalance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(accountBalance: IAccountBalance): IAccountBalance {
    const copy: IAccountBalance = Object.assign({}, accountBalance, {
      createdAt: accountBalance.createdAt != null && accountBalance.createdAt.isValid() ? accountBalance.createdAt.toJSON() : null,
      updatedAt: accountBalance.updatedAt != null && accountBalance.updatedAt.isValid() ? accountBalance.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((accountBalance: IAccountBalance) => {
        accountBalance.createdAt = accountBalance.createdAt != null ? moment(accountBalance.createdAt) : null;
        accountBalance.updatedAt = accountBalance.updatedAt != null ? moment(accountBalance.updatedAt) : null;
      });
    }
    return res;
  }
}
