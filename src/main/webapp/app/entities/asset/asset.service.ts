import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAsset } from 'app/shared/model/asset.model';

type EntityResponseType = HttpResponse<IAsset>;
type EntityArrayResponseType = HttpResponse<IAsset[]>;

@Injectable({ providedIn: 'root' })
export class AssetService {
  public resourceUrl = SERVER_API_URL + 'api/assets';

  constructor(protected http: HttpClient) {}

  create(asset: IAsset): Observable<EntityResponseType> {
    return this.http.post<IAsset>(this.resourceUrl, asset, { observe: 'response' });
  }

  update(asset: IAsset): Observable<EntityResponseType> {
    return this.http.put<IAsset>(this.resourceUrl, asset, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAsset>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAsset[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
