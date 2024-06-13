import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMensaje, NewMensaje } from '../mensaje.model';

export type PartialUpdateMensaje = Partial<IMensaje> & Pick<IMensaje, 'id'>;

export type EntityResponseType = HttpResponse<IMensaje>;
export type EntityArrayResponseType = HttpResponse<IMensaje[]>;

@Injectable({ providedIn: 'root' })
export class MensajeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/mensajes');

  create(mensaje: NewMensaje): Observable<EntityResponseType> {
    return this.http.post<IMensaje>(this.resourceUrl, mensaje, { observe: 'response' });
  }

  update(mensaje: IMensaje): Observable<EntityResponseType> {
    return this.http.put<IMensaje>(`${this.resourceUrl}/${this.getMensajeIdentifier(mensaje)}`, mensaje, { observe: 'response' });
  }

  partialUpdate(mensaje: PartialUpdateMensaje): Observable<EntityResponseType> {
    return this.http.patch<IMensaje>(`${this.resourceUrl}/${this.getMensajeIdentifier(mensaje)}`, mensaje, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMensaje>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMensaje[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getMensajeIdentifier(mensaje: Pick<IMensaje, 'id'>): number {
    return mensaje.id;
  }

  compareMensaje(o1: Pick<IMensaje, 'id'> | null, o2: Pick<IMensaje, 'id'> | null): boolean {
    return o1 && o2 ? this.getMensajeIdentifier(o1) === this.getMensajeIdentifier(o2) : o1 === o2;
  }

  addMensajeToCollectionIfMissing<Type extends Pick<IMensaje, 'id'>>(
    mensajeCollection: Type[],
    ...mensajesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const mensajes: Type[] = mensajesToCheck.filter(isPresent);
    if (mensajes.length > 0) {
      const mensajeCollectionIdentifiers = mensajeCollection.map(mensajeItem => this.getMensajeIdentifier(mensajeItem));
      const mensajesToAdd = mensajes.filter(mensajeItem => {
        const mensajeIdentifier = this.getMensajeIdentifier(mensajeItem);
        if (mensajeCollectionIdentifiers.includes(mensajeIdentifier)) {
          return false;
        }
        mensajeCollectionIdentifiers.push(mensajeIdentifier);
        return true;
      });
      return [...mensajesToAdd, ...mensajeCollection];
    }
    return mensajeCollection;
  }
}
