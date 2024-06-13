import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFinanzasIngreso, NewFinanzasIngreso } from '../finanzas-ingreso.model';

export type PartialUpdateFinanzasIngreso = Partial<IFinanzasIngreso> & Pick<IFinanzasIngreso, 'id'>;

type RestOf<T extends IFinanzasIngreso | NewFinanzasIngreso> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestFinanzasIngreso = RestOf<IFinanzasIngreso>;

export type NewRestFinanzasIngreso = RestOf<NewFinanzasIngreso>;

export type PartialUpdateRestFinanzasIngreso = RestOf<PartialUpdateFinanzasIngreso>;

export type EntityResponseType = HttpResponse<IFinanzasIngreso>;
export type EntityArrayResponseType = HttpResponse<IFinanzasIngreso[]>;

@Injectable({ providedIn: 'root' })
export class FinanzasIngresoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/finanzas-ingresos');

  create(finanzasIngreso: NewFinanzasIngreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasIngreso);
    return this.http
      .post<RestFinanzasIngreso>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(finanzasIngreso: IFinanzasIngreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasIngreso);
    return this.http
      .put<RestFinanzasIngreso>(`${this.resourceUrl}/${this.getFinanzasIngresoIdentifier(finanzasIngreso)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(finanzasIngreso: PartialUpdateFinanzasIngreso): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(finanzasIngreso);
    return this.http
      .patch<RestFinanzasIngreso>(`${this.resourceUrl}/${this.getFinanzasIngresoIdentifier(finanzasIngreso)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFinanzasIngreso>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFinanzasIngreso[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFinanzasIngresoIdentifier(finanzasIngreso: Pick<IFinanzasIngreso, 'id'>): number {
    return finanzasIngreso.id;
  }

  compareFinanzasIngreso(o1: Pick<IFinanzasIngreso, 'id'> | null, o2: Pick<IFinanzasIngreso, 'id'> | null): boolean {
    return o1 && o2 ? this.getFinanzasIngresoIdentifier(o1) === this.getFinanzasIngresoIdentifier(o2) : o1 === o2;
  }

  addFinanzasIngresoToCollectionIfMissing<Type extends Pick<IFinanzasIngreso, 'id'>>(
    finanzasIngresoCollection: Type[],
    ...finanzasIngresosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const finanzasIngresos: Type[] = finanzasIngresosToCheck.filter(isPresent);
    if (finanzasIngresos.length > 0) {
      const finanzasIngresoCollectionIdentifiers = finanzasIngresoCollection.map(finanzasIngresoItem =>
        this.getFinanzasIngresoIdentifier(finanzasIngresoItem),
      );
      const finanzasIngresosToAdd = finanzasIngresos.filter(finanzasIngresoItem => {
        const finanzasIngresoIdentifier = this.getFinanzasIngresoIdentifier(finanzasIngresoItem);
        if (finanzasIngresoCollectionIdentifiers.includes(finanzasIngresoIdentifier)) {
          return false;
        }
        finanzasIngresoCollectionIdentifiers.push(finanzasIngresoIdentifier);
        return true;
      });
      return [...finanzasIngresosToAdd, ...finanzasIngresoCollection];
    }
    return finanzasIngresoCollection;
  }

  protected convertDateFromClient<T extends IFinanzasIngreso | NewFinanzasIngreso | PartialUpdateFinanzasIngreso>(
    finanzasIngreso: T,
  ): RestOf<T> {
    return {
      ...finanzasIngreso,
      fecha: finanzasIngreso.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restFinanzasIngreso: RestFinanzasIngreso): IFinanzasIngreso {
    return {
      ...restFinanzasIngreso,
      fecha: restFinanzasIngreso.fecha ? dayjs(restFinanzasIngreso.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestFinanzasIngreso>): HttpResponse<IFinanzasIngreso> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestFinanzasIngreso[]>): HttpResponse<IFinanzasIngreso[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
