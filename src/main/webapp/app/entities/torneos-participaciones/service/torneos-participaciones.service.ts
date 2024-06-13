import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITorneosParticipaciones, NewTorneosParticipaciones } from '../torneos-participaciones.model';

export type PartialUpdateTorneosParticipaciones = Partial<ITorneosParticipaciones> & Pick<ITorneosParticipaciones, 'id'>;

type RestOf<T extends ITorneosParticipaciones | NewTorneosParticipaciones> = Omit<T, 'fecha'> & {
  fecha?: string | null;
};

export type RestTorneosParticipaciones = RestOf<ITorneosParticipaciones>;

export type NewRestTorneosParticipaciones = RestOf<NewTorneosParticipaciones>;

export type PartialUpdateRestTorneosParticipaciones = RestOf<PartialUpdateTorneosParticipaciones>;

export type EntityResponseType = HttpResponse<ITorneosParticipaciones>;
export type EntityArrayResponseType = HttpResponse<ITorneosParticipaciones[]>;

@Injectable({ providedIn: 'root' })
export class TorneosParticipacionesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/torneos-participaciones');

  create(torneosParticipaciones: NewTorneosParticipaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneosParticipaciones);
    return this.http
      .post<RestTorneosParticipaciones>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(torneosParticipaciones: ITorneosParticipaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneosParticipaciones);
    return this.http
      .put<RestTorneosParticipaciones>(`${this.resourceUrl}/${this.getTorneosParticipacionesIdentifier(torneosParticipaciones)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(torneosParticipaciones: PartialUpdateTorneosParticipaciones): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(torneosParticipaciones);
    return this.http
      .patch<RestTorneosParticipaciones>(`${this.resourceUrl}/${this.getTorneosParticipacionesIdentifier(torneosParticipaciones)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTorneosParticipaciones>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTorneosParticipaciones[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTorneosParticipacionesIdentifier(torneosParticipaciones: Pick<ITorneosParticipaciones, 'id'>): number {
    return torneosParticipaciones.id;
  }

  compareTorneosParticipaciones(o1: Pick<ITorneosParticipaciones, 'id'> | null, o2: Pick<ITorneosParticipaciones, 'id'> | null): boolean {
    return o1 && o2 ? this.getTorneosParticipacionesIdentifier(o1) === this.getTorneosParticipacionesIdentifier(o2) : o1 === o2;
  }

  addTorneosParticipacionesToCollectionIfMissing<Type extends Pick<ITorneosParticipaciones, 'id'>>(
    torneosParticipacionesCollection: Type[],
    ...torneosParticipacionesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const torneosParticipaciones: Type[] = torneosParticipacionesToCheck.filter(isPresent);
    if (torneosParticipaciones.length > 0) {
      const torneosParticipacionesCollectionIdentifiers = torneosParticipacionesCollection.map(torneosParticipacionesItem =>
        this.getTorneosParticipacionesIdentifier(torneosParticipacionesItem),
      );
      const torneosParticipacionesToAdd = torneosParticipaciones.filter(torneosParticipacionesItem => {
        const torneosParticipacionesIdentifier = this.getTorneosParticipacionesIdentifier(torneosParticipacionesItem);
        if (torneosParticipacionesCollectionIdentifiers.includes(torneosParticipacionesIdentifier)) {
          return false;
        }
        torneosParticipacionesCollectionIdentifiers.push(torneosParticipacionesIdentifier);
        return true;
      });
      return [...torneosParticipacionesToAdd, ...torneosParticipacionesCollection];
    }
    return torneosParticipacionesCollection;
  }

  protected convertDateFromClient<T extends ITorneosParticipaciones | NewTorneosParticipaciones | PartialUpdateTorneosParticipaciones>(
    torneosParticipaciones: T,
  ): RestOf<T> {
    return {
      ...torneosParticipaciones,
      fecha: torneosParticipaciones.fecha?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restTorneosParticipaciones: RestTorneosParticipaciones): ITorneosParticipaciones {
    return {
      ...restTorneosParticipaciones,
      fecha: restTorneosParticipaciones.fecha ? dayjs(restTorneosParticipaciones.fecha) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTorneosParticipaciones>): HttpResponse<ITorneosParticipaciones> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTorneosParticipaciones[]>): HttpResponse<ITorneosParticipaciones[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
