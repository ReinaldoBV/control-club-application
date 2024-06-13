import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPartido, NewPartido } from '../partido.model';

export type PartialUpdatePartido = Partial<IPartido> & Pick<IPartido, 'id'>;

type RestOf<T extends IPartido | NewPartido> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestPartido = RestOf<IPartido>;

export type NewRestPartido = RestOf<NewPartido>;

export type PartialUpdateRestPartido = RestOf<PartialUpdatePartido>;

export type EntityResponseType = HttpResponse<IPartido>;
export type EntityArrayResponseType = HttpResponse<IPartido[]>;

@Injectable({ providedIn: 'root' })
export class PartidoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/partidos');

  create(partido: NewPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partido);
    return this.http
      .post<RestPartido>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(partido: IPartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partido);
    return this.http
      .put<RestPartido>(`${this.resourceUrl}/${this.getPartidoIdentifier(partido)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(partido: PartialUpdatePartido): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(partido);
    return this.http
      .patch<RestPartido>(`${this.resourceUrl}/${this.getPartidoIdentifier(partido)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPartido>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPartido[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPartidoIdentifier(partido: Pick<IPartido, 'id'>): number {
    return partido.id;
  }

  comparePartido(o1: Pick<IPartido, 'id'> | null, o2: Pick<IPartido, 'id'> | null): boolean {
    return o1 && o2 ? this.getPartidoIdentifier(o1) === this.getPartidoIdentifier(o2) : o1 === o2;
  }

  addPartidoToCollectionIfMissing<Type extends Pick<IPartido, 'id'>>(
    partidoCollection: Type[],
    ...partidosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const partidos: Type[] = partidosToCheck.filter(isPresent);
    if (partidos.length > 0) {
      const partidoCollectionIdentifiers = partidoCollection.map(partidoItem => this.getPartidoIdentifier(partidoItem));
      const partidosToAdd = partidos.filter(partidoItem => {
        const partidoIdentifier = this.getPartidoIdentifier(partidoItem);
        if (partidoCollectionIdentifiers.includes(partidoIdentifier)) {
          return false;
        }
        partidoCollectionIdentifiers.push(partidoIdentifier);
        return true;
      });
      return [...partidosToAdd, ...partidoCollection];
    }
    return partidoCollection;
  }

  protected convertDateFromClient<T extends IPartido | NewPartido | PartialUpdatePartido>(partido: T): RestOf<T> {
    return {
      ...partido,
      fecha: partido.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPartido: RestPartido): IPartido {
    return {
      ...restPartido,
      fecha: restPartido.fecha ? dayjs(restPartido.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPartido>): HttpResponse<IPartido> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPartido[]>): HttpResponse<IPartido[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
