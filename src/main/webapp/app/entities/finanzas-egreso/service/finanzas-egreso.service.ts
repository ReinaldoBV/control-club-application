import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFinanzasEgreso, NewFinanzasEgreso } from '../finanzas-egreso.model';

export type PartialUpdateFinanzasEgreso = Partial<IFinanzasEgreso> & Pick<IFinanzasEgreso, 'id'>;

type RestOf<T extends IFinanzasEgreso | NewFinanzasEgreso> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestFinanzasEgreso = RestOf<IFinanzasEgreso>;

export type NewRestFinanzasEgreso = RestOf<NewFinanzasEgreso>;

export type PartialUpdateRestFinanzasEgreso = RestOf<PartialUpdateFinanzasEgreso>;

export type EntityResponseType = HttpResponse<IFinanzasEgreso>;
export type EntityArrayResponseType = HttpResponse<IFinanzasEgreso[]>;

@Injectable({ providedIn: 'root' })
export class FinanzasEgresoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/finanzas-egresos');

  create(finanzasEgreso: NewFinanzasEgreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasEgreso);
    return this.http
      .post<RestFinanzasEgreso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(finanzasEgreso: IFinanzasEgreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasEgreso);
    return this.http
      .put<RestFinanzasEgreso>(`${this.resourceUrl}/${this.getFinanzasEgresoIdentifier(finanzasEgreso)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(finanzasEgreso: PartialUpdateFinanzasEgreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasEgreso);
    return this.http
      .patch<RestFinanzasEgreso>(`${this.resourceUrl}/${this.getFinanzasEgresoIdentifier(finanzasEgreso)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFinanzasEgreso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFinanzasEgreso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFinanzasEgresoIdentifier(finanzasEgreso: Pick<IFinanzasEgreso, 'id'>): number {
    return finanzasEgreso.id;
  }

  compareFinanzasEgreso(o1: Pick<IFinanzasEgreso, 'id'> | null, o2: Pick<IFinanzasEgreso, 'id'> | null): boolean {
    return o1 && o2 ? this.getFinanzasEgresoIdentifier(o1) === this.getFinanzasEgresoIdentifier(o2) : o1 === o2;
  }

  addFinanzasEgresoToCollectionIfMissing<Type extends Pick<IFinanzasEgreso, 'id'>>(
    finanzasEgresoCollection: Type[],
    ...finanzasEgresosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const finanzasEgresos: Type[] = finanzasEgresosToCheck.filter(isPresent);
    if (finanzasEgresos.length > 0) {
      const finanzasEgresoCollectionIdentifiers = finanzasEgresoCollection.map(finanzasEgresoItem =>
        this.getFinanzasEgresoIdentifier(finanzasEgresoItem),
      );
      const finanzasEgresosToAdd = finanzasEgresos.filter(finanzasEgresoItem => {
        const finanzasEgresoIdentifier = this.getFinanzasEgresoIdentifier(finanzasEgresoItem);
        if (finanzasEgresoCollectionIdentifiers.includes(finanzasEgresoIdentifier)) {
          return false;
        }
        finanzasEgresoCollectionIdentifiers.push(finanzasEgresoIdentifier);
        return true;
      });
      return [...finanzasEgresosToAdd, ...finanzasEgresoCollection];
    }
    return finanzasEgresoCollection;
  }

  protected convertDateFromClient<T extends IFinanzasEgreso | NewFinanzasEgreso | PartialUpdateFinanzasEgreso>(
    finanzasEgreso: T,
  ): RestOf<T> {
    return {
      ...finanzasEgreso,
      fecha: finanzasEgreso.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFinanzasEgreso: RestFinanzasEgreso): IFinanzasEgreso {
    return {
      ...restFinanzasEgreso,
      fecha: restFinanzasEgreso.fecha ? dayjs(restFinanzasEgreso.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFinanzasEgreso>): HttpResponse<IFinanzasEgreso> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFinanzasEgreso[]>): HttpResponse<IFinanzasEgreso[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
