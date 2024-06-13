import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBienes, NewBienes } from '../bienes.model';

export type PartialUpdateBienes = Partial<IBienes> & Pick<IBienes, 'id'>;

export type EntityResponseType = HttpResponse<IBienes>;
export type EntityArrayResponseType = HttpResponse<IBienes[]>;

@Injectable({ providedIn: 'root' })
export class BienesService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bienes');

  create(bienes: NewBienes): Observable<EntityResponseType> {
    return this.http.post<IBienes>(this.resourceUrl, bienes, { observe: 'response' });
  }

  update(bienes: IBienes): Observable<EntityResponseType> {
    return this.http.put<IBienes>(`${this.resourceUrl}/${this.getBienesIdentifier(bienes)}`, bienes, { observe: 'response' });
  }

  partialUpdate(bienes: PartialUpdateBienes): Observable<EntityResponseType> {
    return this.http.patch<IBienes>(`${this.resourceUrl}/${this.getBienesIdentifier(bienes)}`, bienes, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBienes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBienes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBienesIdentifier(bienes: Pick<IBienes, 'id'>): number {
    return bienes.id;
  }

  compareBienes(o1: Pick<IBienes, 'id'> | null, o2: Pick<IBienes, 'id'> | null): boolean {
    return o1 && o2 ? this.getBienesIdentifier(o1) === this.getBienesIdentifier(o2) : o1 === o2;
  }

  addBienesToCollectionIfMissing<Type extends Pick<IBienes, 'id'>>(
    bienesCollection: Type[],
    ...bienesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bienes: Type[] = bienesToCheck.filter(isPresent);
    if (bienes.length > 0) {
      const bienesCollectionIdentifiers = bienesCollection.map(bienesItem => this.getBienesIdentifier(bienesItem));
      const bienesToAdd = bienes.filter(bienesItem => {
        const bienesIdentifier = this.getBienesIdentifier(bienesItem);
        if (bienesCollectionIdentifiers.includes(bienesIdentifier)) {
          return false;
        }
        bienesCollectionIdentifiers.push(bienesIdentifier);
        return true;
      });
      return [...bienesToAdd, ...bienesCollection];
    }
    return bienesCollection;
  }
}
