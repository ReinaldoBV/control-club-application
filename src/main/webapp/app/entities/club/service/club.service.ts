import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IClub, NewClub } from '../club.model';

export type PartialUpdateClub = Partial<IClub> & Pick<IClub, 'id'>;

type RestOf<T extends IClub | NewClub> = Omit<T, 'fechaFundacion'> & {
  fechaFundacion?: string | null;
};

export type RestClub = RestOf<IClub>;

export type NewRestClub = RestOf<NewClub>;

export type PartialUpdateRestClub = RestOf<PartialUpdateClub>;

export type EntityResponseType = HttpResponse<IClub>;
export type EntityArrayResponseType = HttpResponse<IClub[]>;

@Injectable({ providedIn: 'root' })
export class ClubService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/clubs');

  create(club: NewClub): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(club);
    return this.http.post<RestClub>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(club: IClub): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(club);
    return this.http
      .put<RestClub>(`${this.resourceUrl}/${this.getClubIdentifier(club)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(club: PartialUpdateClub): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(club);
    return this.http
      .patch<RestClub>(`${this.resourceUrl}/${this.getClubIdentifier(club)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestClub>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestClub[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getClubIdentifier(club: Pick<IClub, 'id'>): number {
    return club.id;
  }

  compareClub(o1: Pick<IClub, 'id'> | null, o2: Pick<IClub, 'id'> | null): boolean {
    return o1 && o2 ? this.getClubIdentifier(o1) === this.getClubIdentifier(o2) : o1 === o2;
  }

  addClubToCollectionIfMissing<Type extends Pick<IClub, 'id'>>(
    clubCollection: Type[],
    ...clubsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const clubs: Type[] = clubsToCheck.filter(isPresent);
    if (clubs.length > 0) {
      const clubCollectionIdentifiers = clubCollection.map(clubItem => this.getClubIdentifier(clubItem));
      const clubsToAdd = clubs.filter(clubItem => {
        const clubIdentifier = this.getClubIdentifier(clubItem);
        if (clubCollectionIdentifiers.includes(clubIdentifier)) {
          return false;
        }
        clubCollectionIdentifiers.push(clubIdentifier);
        return true;
      });
      return [...clubsToAdd, ...clubCollection];
    }
    return clubCollection;
  }

  protected convertDateFromClient<T extends IClub | NewClub | PartialUpdateClub>(club: T): RestOf<T> {
    return {
      ...club,
      fechaFundacion: club.fechaFundacion?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restClub: RestClub): IClub {
    return {
      ...restClub,
      fechaFundacion: restClub.fechaFundacion ? dayjs(restClub.fechaFundacion) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestClub>): HttpResponse<IClub> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestClub[]>): HttpResponse<IClub[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
