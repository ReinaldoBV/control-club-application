import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPrevisionSalud, NewPrevisionSalud } from '../prevision-salud.model';

export type PartialUpdatePrevisionSalud = Partial<IPrevisionSalud> & Pick<IPrevisionSalud, 'id'>;

export type EntityResponseType = HttpResponse<IPrevisionSalud>;
export type EntityArrayResponseType = HttpResponse<IPrevisionSalud[]>;

@Injectable({ providedIn: 'root' })
export class PrevisionSaludService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/prevision-saluds');

  create(previsionSalud: NewPrevisionSalud): Observable<EntityResponseType> {
    return this.http.post<IPrevisionSalud>(this.resourceUrl, previsionSalud, { observe: 'response' });
  }

  update(previsionSalud: IPrevisionSalud): Observable<EntityResponseType> {
    return this.http.put<IPrevisionSalud>(`${this.resourceUrl}/${this.getPrevisionSaludIdentifier(previsionSalud)}`, previsionSalud, {
      observe: 'response',
    });
  }

  partialUpdate(previsionSalud: PartialUpdatePrevisionSalud): Observable<EntityResponseType> {
    return this.http.patch<IPrevisionSalud>(`${this.resourceUrl}/${this.getPrevisionSaludIdentifier(previsionSalud)}`, previsionSalud, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPrevisionSalud>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPrevisionSalud[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPrevisionSaludIdentifier(previsionSalud: Pick<IPrevisionSalud, 'id'>): number {
    return previsionSalud.id;
  }

  comparePrevisionSalud(o1: Pick<IPrevisionSalud, 'id'> | null, o2: Pick<IPrevisionSalud, 'id'> | null): boolean {
    return o1 && o2 ? this.getPrevisionSaludIdentifier(o1) === this.getPrevisionSaludIdentifier(o2) : o1 === o2;
  }

  addPrevisionSaludToCollectionIfMissing<Type extends Pick<IPrevisionSalud, 'id'>>(
    previsionSaludCollection: Type[],
    ...previsionSaludsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const previsionSaluds: Type[] = previsionSaludsToCheck.filter(isPresent);
    if (previsionSaluds.length > 0) {
      const previsionSaludCollectionIdentifiers = previsionSaludCollection.map(previsionSaludItem =>
        this.getPrevisionSaludIdentifier(previsionSaludItem),
      );
      const previsionSaludsToAdd = previsionSaluds.filter(previsionSaludItem => {
        const previsionSaludIdentifier = this.getPrevisionSaludIdentifier(previsionSaludItem);
        if (previsionSaludCollectionIdentifiers.includes(previsionSaludIdentifier)) {
          return false;
        }
        previsionSaludCollectionIdentifiers.push(previsionSaludIdentifier);
        return true;
      });
      return [...previsionSaludsToAdd, ...previsionSaludCollection];
    }
    return previsionSaludCollection;
  }
}
