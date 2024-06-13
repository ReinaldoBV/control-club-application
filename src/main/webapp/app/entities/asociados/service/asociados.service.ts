import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAsociados, NewAsociados } from '../asociados.model';

export type PartialUpdateAsociados = Partial<IAsociados> & Pick<IAsociados, 'id'>;

type RestOf<T extends IAsociados | NewAsociados> = Omit<T, 'fechaAsoc'> & {
  fechaAsoc?: string | null;
};

export type RestAsociados = RestOf<IAsociados>;

export type NewRestAsociados = RestOf<NewAsociados>;

export type PartialUpdateRestAsociados = RestOf<PartialUpdateAsociados>;

export type EntityResponseType = HttpResponse<IAsociados>;
export type EntityArrayResponseType = HttpResponse<IAsociados[]>;

@Injectable({ providedIn: 'root' })
export class AsociadosService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/asociados');

  create(asociados: NewAsociados): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(asociados);
    return this.http
      .post<RestAsociados>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(asociados: IAsociados): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(asociados);
    return this.http
      .put<RestAsociados>(`${this.resourceUrl}/${this.getAsociadosIdentifier(asociados)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(asociados: PartialUpdateAsociados): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(asociados);
    return this.http
      .patch<RestAsociados>(`${this.resourceUrl}/${this.getAsociadosIdentifier(asociados)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAsociados>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAsociados[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAsociadosIdentifier(asociados: Pick<IAsociados, 'id'>): number {
    return asociados.id;
  }

  compareAsociados(o1: Pick<IAsociados, 'id'> | null, o2: Pick<IAsociados, 'id'> | null): boolean {
    return o1 && o2 ? this.getAsociadosIdentifier(o1) === this.getAsociadosIdentifier(o2) : o1 === o2;
  }

  addAsociadosToCollectionIfMissing<Type extends Pick<IAsociados, 'id'>>(
    asociadosCollection: Type[],
    ...asociadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const asociados: Type[] = asociadosToCheck.filter(isPresent);
    if (asociados.length > 0) {
      const asociadosCollectionIdentifiers = asociadosCollection.map(asociadosItem => this.getAsociadosIdentifier(asociadosItem));
      const asociadosToAdd = asociados.filter(asociadosItem => {
        const asociadosIdentifier = this.getAsociadosIdentifier(asociadosItem);
        if (asociadosCollectionIdentifiers.includes(asociadosIdentifier)) {
          return false;
        }
        asociadosCollectionIdentifiers.push(asociadosIdentifier);
        return true;
      });
      return [...asociadosToAdd, ...asociadosCollection];
    }
    return asociadosCollection;
  }

  protected convertDateFromClient<T extends IAsociados | NewAsociados | PartialUpdateAsociados>(asociados: T): RestOf<T> {
    return {
      ...asociados,
      fechaAsoc: asociados.fechaAsoc?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restAsociados: RestAsociados): IAsociados {
    return {
      ...restAsociados,
      fechaAsoc: restAsociados.fechaAsoc ? dayjs(restAsociados.fechaAsoc) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAsociados>): HttpResponse<IAsociados> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAsociados[]>): HttpResponse<IAsociados[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
