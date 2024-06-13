import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEntrenamiento, NewEntrenamiento } from '../entrenamiento.model';

export type PartialUpdateEntrenamiento = Partial<IEntrenamiento> & Pick<IEntrenamiento, 'id'>;

type RestOf<T extends IEntrenamiento | NewEntrenamiento> = Omit<T, 'fechaHora'> & {
  fechaHora?: string | null;
};

export type RestEntrenamiento = RestOf<IEntrenamiento>;

export type NewRestEntrenamiento = RestOf<NewEntrenamiento>;

export type PartialUpdateRestEntrenamiento = RestOf<PartialUpdateEntrenamiento>;

export type EntityResponseType = HttpResponse<IEntrenamiento>;
export type EntityArrayResponseType = HttpResponse<IEntrenamiento[]>;

@Injectable({ providedIn: 'root' })
export class EntrenamientoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/entrenamientos');

  create(entrenamiento: NewEntrenamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrenamiento);
    return this.http
      .post<RestEntrenamiento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(entrenamiento: IEntrenamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrenamiento);
    return this.http
      .put<RestEntrenamiento>(`${this.resourceUrl}/${this.getEntrenamientoIdentifier(entrenamiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(entrenamiento: PartialUpdateEntrenamiento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(entrenamiento);
    return this.http
      .patch<RestEntrenamiento>(`${this.resourceUrl}/${this.getEntrenamientoIdentifier(entrenamiento)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEntrenamiento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEntrenamiento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEntrenamientoIdentifier(entrenamiento: Pick<IEntrenamiento, 'id'>): number {
    return entrenamiento.id;
  }

  compareEntrenamiento(o1: Pick<IEntrenamiento, 'id'> | null, o2: Pick<IEntrenamiento, 'id'> | null): boolean {
    return o1 && o2 ? this.getEntrenamientoIdentifier(o1) === this.getEntrenamientoIdentifier(o2) : o1 === o2;
  }

  addEntrenamientoToCollectionIfMissing<Type extends Pick<IEntrenamiento, 'id'>>(
    entrenamientoCollection: Type[],
    ...entrenamientosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const entrenamientos: Type[] = entrenamientosToCheck.filter(isPresent);
    if (entrenamientos.length > 0) {
      const entrenamientoCollectionIdentifiers = entrenamientoCollection.map(entrenamientoItem =>
        this.getEntrenamientoIdentifier(entrenamientoItem),
      );
      const entrenamientosToAdd = entrenamientos.filter(entrenamientoItem => {
        const entrenamientoIdentifier = this.getEntrenamientoIdentifier(entrenamientoItem);
        if (entrenamientoCollectionIdentifiers.includes(entrenamientoIdentifier)) {
          return false;
        }
        entrenamientoCollectionIdentifiers.push(entrenamientoIdentifier);
        return true;
      });
      return [...entrenamientosToAdd, ...entrenamientoCollection];
    }
    return entrenamientoCollection;
  }

  protected convertDateFromClient<T extends IEntrenamiento | NewEntrenamiento | PartialUpdateEntrenamiento>(entrenamiento: T): RestOf<T> {
    return {
      ...entrenamiento,
      fechaHora: entrenamiento.fechaHora?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restEntrenamiento: RestEntrenamiento): IEntrenamiento {
    return {
      ...restEntrenamiento,
      fechaHora: restEntrenamiento.fechaHora ? dayjs(restEntrenamiento.fechaHora) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEntrenamiento>): HttpResponse<IEntrenamiento> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEntrenamiento[]>): HttpResponse<IEntrenamiento[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
