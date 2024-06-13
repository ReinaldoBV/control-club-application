import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IJugador, NewJugador } from '../jugador.model';

export type PartialUpdateJugador = Partial<IJugador> & Pick<IJugador, 'id'>;

type RestOf<T extends IJugador | NewJugador> = Omit<T, 'fechaNacimiento'> & {
  fechaNacimiento?: string | null;
};

export type RestJugador = RestOf<IJugador>;

export type NewRestJugador = RestOf<NewJugador>;

export type PartialUpdateRestJugador = RestOf<PartialUpdateJugador>;

export type EntityResponseType = HttpResponse<IJugador>;
export type EntityArrayResponseType = HttpResponse<IJugador[]>;

@Injectable({ providedIn: 'root' })
export class JugadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/jugadors');

  create(jugador: NewJugador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jugador);
    return this.http
      .post<RestJugador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(jugador: IJugador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jugador);
    return this.http
      .put<RestJugador>(`${this.resourceUrl}/${this.getJugadorIdentifier(jugador)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(jugador: PartialUpdateJugador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(jugador);
    return this.http
      .patch<RestJugador>(`${this.resourceUrl}/${this.getJugadorIdentifier(jugador)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestJugador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestJugador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getJugadorIdentifier(jugador: Pick<IJugador, 'id'>): number {
    return jugador.id;
  }

  compareJugador(o1: Pick<IJugador, 'id'> | null, o2: Pick<IJugador, 'id'> | null): boolean {
    return o1 && o2 ? this.getJugadorIdentifier(o1) === this.getJugadorIdentifier(o2) : o1 === o2;
  }

  addJugadorToCollectionIfMissing<Type extends Pick<IJugador, 'id'>>(
    jugadorCollection: Type[],
    ...jugadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const jugadors: Type[] = jugadorsToCheck.filter(isPresent);
    if (jugadors.length > 0) {
      const jugadorCollectionIdentifiers = jugadorCollection.map(jugadorItem => this.getJugadorIdentifier(jugadorItem));
      const jugadorsToAdd = jugadors.filter(jugadorItem => {
        const jugadorIdentifier = this.getJugadorIdentifier(jugadorItem);
        if (jugadorCollectionIdentifiers.includes(jugadorIdentifier)) {
          return false;
        }
        jugadorCollectionIdentifiers.push(jugadorIdentifier);
        return true;
      });
      return [...jugadorsToAdd, ...jugadorCollection];
    }
    return jugadorCollection;
  }

  protected convertDateFromClient<T extends IJugador | NewJugador | PartialUpdateJugador>(jugador: T): RestOf<T> {
    return {
      ...jugador,
      fechaNacimiento: jugador.fechaNacimiento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restJugador: RestJugador): IJugador {
    return {
      ...restJugador,
      fechaNacimiento: restJugador.fechaNacimiento ? dayjs(restJugador.fechaNacimiento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestJugador>): HttpResponse<IJugador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestJugador[]>): HttpResponse<IJugador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
