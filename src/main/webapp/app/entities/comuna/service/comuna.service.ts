import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IComuna, NewComuna } from '../comuna.model';

export type PartialUpdateComuna = Partial<IComuna> & Pick<IComuna, 'id'>;

export type EntityResponseType = HttpResponse<IComuna>;
export type EntityArrayResponseType = HttpResponse<IComuna[]>;

@Injectable({ providedIn: 'root' })
export class ComunaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/comunas');

  create(comuna: NewComuna): Observable<EntityResponseType> {
    return this.http.post<IComuna>(this.resourceUrl, comuna, { observe: 'response' });
  }

  update(comuna: IComuna): Observable<EntityResponseType> {
    return this.http.put<IComuna>(`${this.resourceUrl}/${this.getComunaIdentifier(comuna)}`, comuna, { observe: 'response' });
  }

  partialUpdate(comuna: PartialUpdateComuna): Observable<EntityResponseType> {
    return this.http.patch<IComuna>(`${this.resourceUrl}/${this.getComunaIdentifier(comuna)}`, comuna, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IComuna>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IComuna[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getComunaIdentifier(comuna: Pick<IComuna, 'id'>): number {
    return comuna.id;
  }

  compareComuna(o1: Pick<IComuna, 'id'> | null, o2: Pick<IComuna, 'id'> | null): boolean {
    return o1 && o2 ? this.getComunaIdentifier(o1) === this.getComunaIdentifier(o2) : o1 === o2;
  }

  addComunaToCollectionIfMissing<Type extends Pick<IComuna, 'id'>>(
    comunaCollection: Type[],
    ...comunasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const comunas: Type[] = comunasToCheck.filter(isPresent);
    if (comunas.length > 0) {
      const comunaCollectionIdentifiers = comunaCollection.map(comunaItem => this.getComunaIdentifier(comunaItem));
      const comunasToAdd = comunas.filter(comunaItem => {
        const comunaIdentifier = this.getComunaIdentifier(comunaItem);
        if (comunaCollectionIdentifiers.includes(comunaIdentifier)) {
          return false;
        }
        comunaCollectionIdentifiers.push(comunaIdentifier);
        return true;
      });
      return [...comunasToAdd, ...comunaCollection];
    }
    return comunaCollection;
  }
}
