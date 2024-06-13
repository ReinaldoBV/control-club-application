import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICentroEducativo, NewCentroEducativo } from '../centro-educativo.model';

export type PartialUpdateCentroEducativo = Partial<ICentroEducativo> & Pick<ICentroEducativo, 'id'>;

export type EntityResponseType = HttpResponse<ICentroEducativo>;
export type EntityArrayResponseType = HttpResponse<ICentroEducativo[]>;

@Injectable({ providedIn: 'root' })
export class CentroEducativoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/centro-educativos');

  create(centroEducativo: NewCentroEducativo): Observable<EntityResponseType> {
    return this.http.post<ICentroEducativo>(this.resourceUrl, centroEducativo, { observe: 'response' });
  }

  update(centroEducativo: ICentroEducativo): Observable<EntityResponseType> {
    return this.http.put<ICentroEducativo>(`${this.resourceUrl}/${this.getCentroEducativoIdentifier(centroEducativo)}`, centroEducativo, {
      observe: 'response',
    });
  }

  partialUpdate(centroEducativo: PartialUpdateCentroEducativo): Observable<EntityResponseType> {
    return this.http.patch<ICentroEducativo>(`${this.resourceUrl}/${this.getCentroEducativoIdentifier(centroEducativo)}`, centroEducativo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICentroEducativo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICentroEducativo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCentroEducativoIdentifier(centroEducativo: Pick<ICentroEducativo, 'id'>): number {
    return centroEducativo.id;
  }

  compareCentroEducativo(o1: Pick<ICentroEducativo, 'id'> | null, o2: Pick<ICentroEducativo, 'id'> | null): boolean {
    return o1 && o2 ? this.getCentroEducativoIdentifier(o1) === this.getCentroEducativoIdentifier(o2) : o1 === o2;
  }

  addCentroEducativoToCollectionIfMissing<Type extends Pick<ICentroEducativo, 'id'>>(
    centroEducativoCollection: Type[],
    ...centroEducativosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const centroEducativos: Type[] = centroEducativosToCheck.filter(isPresent);
    if (centroEducativos.length > 0) {
      const centroEducativoCollectionIdentifiers = centroEducativoCollection.map(centroEducativoItem =>
        this.getCentroEducativoIdentifier(centroEducativoItem),
      );
      const centroEducativosToAdd = centroEducativos.filter(centroEducativoItem => {
        const centroEducativoIdentifier = this.getCentroEducativoIdentifier(centroEducativoItem);
        if (centroEducativoCollectionIdentifiers.includes(centroEducativoIdentifier)) {
          return false;
        }
        centroEducativoCollectionIdentifiers.push(centroEducativoIdentifier);
        return true;
      });
      return [...centroEducativosToAdd, ...centroEducativoCollection];
    }
    return centroEducativoCollection;
  }
}
