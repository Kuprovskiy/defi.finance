import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITrustedDevice } from 'app/shared/model/trusted-device.model';

type EntityResponseType = HttpResponse<ITrustedDevice>;
type EntityArrayResponseType = HttpResponse<ITrustedDevice[]>;

@Injectable({ providedIn: 'root' })
export class TrustedDeviceService {
  public resourceUrl = SERVER_API_URL + 'api/trusted-devices';

  constructor(protected http: HttpClient) {}

  create(trustedDevice: ITrustedDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trustedDevice);
    return this.http
      .post<ITrustedDevice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(trustedDevice: ITrustedDevice): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(trustedDevice);
    return this.http
      .put<ITrustedDevice>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITrustedDevice>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITrustedDevice[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(trustedDevice: ITrustedDevice): ITrustedDevice {
    const copy: ITrustedDevice = Object.assign({}, trustedDevice, {
      createdAt: trustedDevice.createdAt != null && trustedDevice.createdAt.isValid() ? trustedDevice.createdAt.toJSON() : null
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
      res.body.forEach((trustedDevice: ITrustedDevice) => {
        trustedDevice.createdAt = trustedDevice.createdAt != null ? moment(trustedDevice.createdAt) : null;
      });
    }
    return res;
  }
}
