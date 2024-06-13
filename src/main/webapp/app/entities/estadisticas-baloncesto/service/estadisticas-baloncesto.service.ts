import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';

export type PartialUpdateEstadisticasBaloncesto = Partial<IEstadisticasBaloncesto> & Pick<IEstadisticasBaloncesto, 'id'>;

export type EntityResponseType = HttpResponse<IEstadisticasBaloncesto>;
export type EntityArrayResponseType = HttpResponse<IEstadisticasBaloncesto[]>;

@Injectable({ providedIn: 'root' })
export class EstadisticasBaloncestoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estadisticas-baloncestos');

  create(estadisticasBaloncesto: NewEstadisticasBaloncesto): Observable<EntityResponseType> {
    return this.http.post<IEstadisticasBaloncesto>(this.resourceUrl, estadisticasBaloncesto, { observe: 'response' });
  }

  update(estadisticasBaloncesto: IEstadisticasBaloncesto): Observable<EntityResponseType> {
    return this.http.put<IEstadisticasBaloncesto>(
      `${this.resourceUrl}/${this.getEstadisticasBaloncestoIdentifier(estadisticasBaloncesto)}`,
      estadisticasBaloncesto,
      { observe: 'response' },
    );
  }

  partialUpdate(estadisticasBaloncesto: PartialUpdateEstadisticasBaloncesto): Observable<EntityResponseType> {
    return this.http.patch<IEstadisticasBaloncesto>(
      `${this.resourceUrl}/${this.getEstadisticasBaloncestoIdentifier(estadisticasBaloncesto)}`,
      estadisticasBaloncesto,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstadisticasBaloncesto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstadisticasBaloncesto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstadisticasBaloncestoIdentifier(estadisticasBaloncesto: Pick<IEstadisticasBaloncesto, 'id'>): number {
    return estadisticasBaloncesto.id;
  }

  compareEstadisticasBaloncesto(o1: Pick<IEstadisticasBaloncesto, 'id'> | null, o2: Pick<IEstadisticasBaloncesto, 'id'> | null): boolean {
    return o1 && o2 ? this.getEstadisticasBaloncestoIdentifier(o1) === this.getEstadisticasBaloncestoIdentifier(o2) : o1 === o2;
  }

  addEstadisticasBaloncestoToCollectionIfMissing<Type extends Pick<IEstadisticasBaloncesto, 'id'>>(
    estadisticasBaloncestoCollection: Type[],
    ...estadisticasBaloncestosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const estadisticasBaloncestos: Type[] = estadisticasBaloncestosToCheck.filter(isPresent);
    if (estadisticasBaloncestos.length > 0) {
      const estadisticasBaloncestoCollectionIdentifiers = estadisticasBaloncestoCollection.map(estadisticasBaloncestoItem =>
        this.getEstadisticasBaloncestoIdentifier(estadisticasBaloncestoItem),
      );
      const estadisticasBaloncestosToAdd = estadisticasBaloncestos.filter(estadisticasBaloncestoItem => {
        const estadisticasBaloncestoIdentifier = this.getEstadisticasBaloncestoIdentifier(estadisticasBaloncestoItem);
        if (estadisticasBaloncestoCollectionIdentifiers.includes(estadisticasBaloncestoIdentifier)) {
          return false;
        }
        estadisticasBaloncestoCollectionIdentifiers.push(estadisticasBaloncestoIdentifier);
        return true;
      });
      return [...estadisticasBaloncestosToAdd, ...estadisticasBaloncestoCollection];
    }
    return estadisticasBaloncestoCollection;
  }
}
