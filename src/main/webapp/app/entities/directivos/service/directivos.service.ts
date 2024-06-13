import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDirectivos, NewDirectivos } from '../directivos.model';

export type PartialUpdateDirectivos = Partial<IDirectivos> & Pick<IDirectivos, 'id'>;

type RestOf<T extends IDirectivos | NewDirectivos> = Omit<T, 'fechaEleccion' | 'fechaVencimiento'> & {
  fechaEleccion?: string | null;
  fechaVencimiento?: string | null;
};

export type RestDirectivos = RestOf<IDirectivos>;

export type NewRestDirectivos = RestOf<NewDirectivos>;

export type PartialUpdateRestDirectivos = RestOf<PartialUpdateDirectivos>;

export type EntityResponseType = HttpResponse<IDirectivos>;
export type EntityArrayResponseType = HttpResponse<IDirectivos[]>;

@Injectable({ providedIn: 'root' })
export class DirectivosService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/directivos');

  create(directivos: NewDirectivos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(directivos);
    return this.http
      .post<RestDirectivos>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(directivos: IDirectivos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(directivos);
    return this.http
      .put<RestDirectivos>(`${this.resourceUrl}/${this.getDirectivosIdentifier(directivos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(directivos: PartialUpdateDirectivos): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(directivos);
    return this.http
      .patch<RestDirectivos>(`${this.resourceUrl}/${this.getDirectivosIdentifier(directivos)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDirectivos>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDirectivos[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDirectivosIdentifier(directivos: Pick<IDirectivos, 'id'>): number {
    return directivos.id;
  }

  compareDirectivos(o1: Pick<IDirectivos, 'id'> | null, o2: Pick<IDirectivos, 'id'> | null): boolean {
    return o1 && o2 ? this.getDirectivosIdentifier(o1) === this.getDirectivosIdentifier(o2) : o1 === o2;
  }

  addDirectivosToCollectionIfMissing<Type extends Pick<IDirectivos, 'id'>>(
    directivosCollection: Type[],
    ...directivosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const directivos: Type[] = directivosToCheck.filter(isPresent);
    if (directivos.length > 0) {
      const directivosCollectionIdentifiers = directivosCollection.map(directivosItem => this.getDirectivosIdentifier(directivosItem));
      const directivosToAdd = directivos.filter(directivosItem => {
        const directivosIdentifier = this.getDirectivosIdentifier(directivosItem);
        if (directivosCollectionIdentifiers.includes(directivosIdentifier)) {
          return false;
        }
        directivosCollectionIdentifiers.push(directivosIdentifier);
        return true;
      });
      return [...directivosToAdd, ...directivosCollection];
    }
    return directivosCollection;
  }

  protected convertDateFromClient<T extends IDirectivos | NewDirectivos | PartialUpdateDirectivos>(directivos: T): RestOf<T> {
    return {
      ...directivos,
      fechaEleccion: directivos.fechaEleccion?.format(DATE_FORMAT) ?? null,
      fechaVencimiento: directivos.fechaVencimiento?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restDirectivos: RestDirectivos): IDirectivos {
    return {
      ...restDirectivos,
      fechaEleccion: restDirectivos.fechaEleccion ? dayjs(restDirectivos.fechaEleccion) : undefined,
      fechaVencimiento: restDirectivos.fechaVencimiento ? dayjs(restDirectivos.fechaVencimiento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDirectivos>): HttpResponse<IDirectivos> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDirectivos[]>): HttpResponse<IDirectivos[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
