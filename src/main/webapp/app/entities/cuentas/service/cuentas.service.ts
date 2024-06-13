import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICuentas, NewCuentas } from '../cuentas.model';

export type PartialUpdateCuentas = Partial<ICuentas> & Pick<ICuentas, 'id'>;

type RestOf<T extends ICuentas | NewCuentas> = Omit<T, 'fechaVencimiento'> & {
  fechaVencimiento?: string | null;
};

export type RestCuentas = RestOf<ICuentas>;

export type NewRestCuentas = RestOf<NewCuentas>;

export type PartialUpdateRestCuentas = RestOf<PartialUpdateCuentas>;

export type EntityResponseType = HttpResponse<ICuentas>;
export type EntityArrayResponseType = HttpResponse<ICuentas[]>;

@Injectable({ providedIn: 'root' })
export class CuentasService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cuentas');

  create(cuentas: NewCuentas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuentas);
    return this.http
      .post<RestCuentas>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cuentas: ICuentas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuentas);
    return this.http
      .put<RestCuentas>(`${this.resourceUrl}/${this.getCuentasIdentifier(cuentas)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cuentas: PartialUpdateCuentas): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuentas);
    return this.http
      .patch<RestCuentas>(`${this.resourceUrl}/${this.getCuentasIdentifier(cuentas)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCuentas>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCuentas[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCuentasIdentifier(cuentas: Pick<ICuentas, 'id'>): number {
    return cuentas.id;
  }

  compareCuentas(o1: Pick<ICuentas, 'id'> | null, o2: Pick<ICuentas, 'id'> | null): boolean {
    return o1 && o2 ? this.getCuentasIdentifier(o1) === this.getCuentasIdentifier(o2) : o1 === o2;
  }

  addCuentasToCollectionIfMissing<Type extends Pick<ICuentas, 'id'>>(
    cuentasCollection: Type[],
    ...cuentasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cuentas: Type[] = cuentasToCheck.filter(isPresent);
    if (cuentas.length > 0) {
      const cuentasCollectionIdentifiers = cuentasCollection.map(cuentasItem => this.getCuentasIdentifier(cuentasItem));
      const cuentasToAdd = cuentas.filter(cuentasItem => {
        const cuentasIdentifier = this.getCuentasIdentifier(cuentasItem);
        if (cuentasCollectionIdentifiers.includes(cuentasIdentifier)) {
          return false;
        }
        cuentasCollectionIdentifiers.push(cuentasIdentifier);
        return true;
      });
      return [...cuentasToAdd, ...cuentasCollection];
    }
    return cuentasCollection;
  }

  protected convertDateFromClient<T extends ICuentas | NewCuentas | PartialUpdateCuentas>(cuentas: T): RestOf<T> {
    return {
      ...cuentas,
      fechaVencimiento: cuentas.fechaVencimiento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCuentas: RestCuentas): ICuentas {
    return {
      ...restCuentas,
      fechaVencimiento: restCuentas.fechaVencimiento ? dayjs(restCuentas.fechaVencimiento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCuentas>): HttpResponse<ICuentas> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCuentas[]>): HttpResponse<ICuentas[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
