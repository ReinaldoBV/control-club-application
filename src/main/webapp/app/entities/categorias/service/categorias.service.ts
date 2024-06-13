import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICategorias, NewCategorias } from '../categorias.model';

export type PartialUpdateCategorias = Partial<ICategorias> & Pick<ICategorias, 'id'>;

export type EntityResponseType = HttpResponse<ICategorias>;
export type EntityArrayResponseType = HttpResponse<ICategorias[]>;

@Injectable({ providedIn: 'root' })
export class CategoriasService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/categorias');

  create(categorias: NewCategorias): Observable<EntityResponseType> {
    return this.http.post<ICategorias>(this.resourceUrl, categorias, { observe: 'response' });
  }

  update(categorias: ICategorias): Observable<EntityResponseType> {
    return this.http.put<ICategorias>(`${this.resourceUrl}/${this.getCategoriasIdentifier(categorias)}`, categorias, {
      observe: 'response',
    });
  }

  partialUpdate(categorias: PartialUpdateCategorias): Observable<EntityResponseType> {
    return this.http.patch<ICategorias>(`${this.resourceUrl}/${this.getCategoriasIdentifier(categorias)}`, categorias, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorias>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorias[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCategoriasIdentifier(categorias: Pick<ICategorias, 'id'>): number {
    return categorias.id;
  }

  compareCategorias(o1: Pick<ICategorias, 'id'> | null, o2: Pick<ICategorias, 'id'> | null): boolean {
    return o1 && o2 ? this.getCategoriasIdentifier(o1) === this.getCategoriasIdentifier(o2) : o1 === o2;
  }

  addCategoriasToCollectionIfMissing<Type extends Pick<ICategorias, 'id'>>(
    categoriasCollection: Type[],
    ...categoriasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const categorias: Type[] = categoriasToCheck.filter(isPresent);
    if (categorias.length > 0) {
      const categoriasCollectionIdentifiers = categoriasCollection.map(categoriasItem => this.getCategoriasIdentifier(categoriasItem));
      const categoriasToAdd = categorias.filter(categoriasItem => {
        const categoriasIdentifier = this.getCategoriasIdentifier(categoriasItem);
        if (categoriasCollectionIdentifiers.includes(categoriasIdentifier)) {
          return false;
        }
        categoriasCollectionIdentifiers.push(categoriasIdentifier);
        return true;
      });
      return [...categoriasToAdd, ...categoriasCollection];
    }
    return categoriasCollection;
  }
}
