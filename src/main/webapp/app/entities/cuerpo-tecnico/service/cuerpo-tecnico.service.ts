import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICuerpoTecnico, NewCuerpoTecnico } from '../cuerpo-tecnico.model';

export type PartialUpdateCuerpoTecnico = Partial<ICuerpoTecnico> & Pick<ICuerpoTecnico, 'id'>;

type RestOf<T extends ICuerpoTecnico | NewCuerpoTecnico> = Omit<T, 'fechaInicio'> & {
  fechaInicio?: string | null;
};

export type RestCuerpoTecnico = RestOf<ICuerpoTecnico>;

export type NewRestCuerpoTecnico = RestOf<NewCuerpoTecnico>;

export type PartialUpdateRestCuerpoTecnico = RestOf<PartialUpdateCuerpoTecnico>;

export type EntityResponseType = HttpResponse<ICuerpoTecnico>;
export type EntityArrayResponseType = HttpResponse<ICuerpoTecnico[]>;

@Injectable({ providedIn: 'root' })
export class CuerpoTecnicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cuerpo-tecnicos');

  create(cuerpoTecnico: NewCuerpoTecnico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuerpoTecnico);
    return this.http
      .post<RestCuerpoTecnico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cuerpoTecnico: ICuerpoTecnico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuerpoTecnico);
    return this.http
      .put<RestCuerpoTecnico>(`${this.resourceUrl}/${this.getCuerpoTecnicoIdentifier(cuerpoTecnico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cuerpoTecnico: PartialUpdateCuerpoTecnico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cuerpoTecnico);
    return this.http
      .patch<RestCuerpoTecnico>(`${this.resourceUrl}/${this.getCuerpoTecnicoIdentifier(cuerpoTecnico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCuerpoTecnico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCuerpoTecnico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCuerpoTecnicoIdentifier(cuerpoTecnico: Pick<ICuerpoTecnico, 'id'>): number {
    return cuerpoTecnico.id;
  }

  compareCuerpoTecnico(o1: Pick<ICuerpoTecnico, 'id'> | null, o2: Pick<ICuerpoTecnico, 'id'> | null): boolean {
    return o1 && o2 ? this.getCuerpoTecnicoIdentifier(o1) === this.getCuerpoTecnicoIdentifier(o2) : o1 === o2;
  }

  addCuerpoTecnicoToCollectionIfMissing<Type extends Pick<ICuerpoTecnico, 'id'>>(
    cuerpoTecnicoCollection: Type[],
    ...cuerpoTecnicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cuerpoTecnicos: Type[] = cuerpoTecnicosToCheck.filter(isPresent);
    if (cuerpoTecnicos.length > 0) {
      const cuerpoTecnicoCollectionIdentifiers = cuerpoTecnicoCollection.map(cuerpoTecnicoItem =>
        this.getCuerpoTecnicoIdentifier(cuerpoTecnicoItem),
      );
      const cuerpoTecnicosToAdd = cuerpoTecnicos.filter(cuerpoTecnicoItem => {
        const cuerpoTecnicoIdentifier = this.getCuerpoTecnicoIdentifier(cuerpoTecnicoItem);
        if (cuerpoTecnicoCollectionIdentifiers.includes(cuerpoTecnicoIdentifier)) {
          return false;
        }
        cuerpoTecnicoCollectionIdentifiers.push(cuerpoTecnicoIdentifier);
        return true;
      });
      return [...cuerpoTecnicosToAdd, ...cuerpoTecnicoCollection];
    }
    return cuerpoTecnicoCollection;
  }

  protected convertDateFromClient<T extends ICuerpoTecnico | NewCuerpoTecnico | PartialUpdateCuerpoTecnico>(cuerpoTecnico: T): RestOf<T> {
    return {
      ...cuerpoTecnico,
      fechaInicio: cuerpoTecnico.fechaInicio?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restCuerpoTecnico: RestCuerpoTecnico): ICuerpoTecnico {
    return {
      ...restCuerpoTecnico,
      fechaInicio: restCuerpoTecnico.fechaInicio ? dayjs(restCuerpoTecnico.fechaInicio) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCuerpoTecnico>): HttpResponse<ICuerpoTecnico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCuerpoTecnico[]>): HttpResponse<ICuerpoTecnico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
