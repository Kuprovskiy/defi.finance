import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWallet } from 'app/shared/model/wallet.model';

type EntityResponseType = HttpResponse<IWallet>;
type EntityArrayResponseType = HttpResponse<IWallet[]>;

@Injectable({ providedIn: 'root' })
export class WalletService {
  public resourceUrl = SERVER_API_URL + 'api/wallets';

  constructor(protected http: HttpClient) {}

  create(wallet: IWallet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wallet);
    return this.http
      .post<IWallet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(wallet: IWallet): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(wallet);
    return this.http
      .put<IWallet>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IWallet>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IWallet[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(wallet: IWallet): IWallet {
    const copy: IWallet = Object.assign({}, wallet, {
      createdAt: wallet.createdAt != null && wallet.createdAt.isValid() ? wallet.createdAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((wallet: IWallet) => {
        wallet.createdAt = wallet.createdAt != null ? moment(wallet.createdAt) : null;
      });
    }
    return res;
  }
}
