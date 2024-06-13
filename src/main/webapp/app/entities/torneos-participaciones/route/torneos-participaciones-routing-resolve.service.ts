import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import { TorneosParticipacionesService } from '../service/torneos-participaciones.service';

const torneosParticipacionesResolve = (route: ActivatedRouteSnapshot): Observable<null | ITorneosParticipaciones> => {
  const id = route.params['id'];
  if (id) {
    return inject(TorneosParticipacionesService)
      .find(id)
      .pipe(
        mergeMap((torneosParticipaciones: HttpResponse<ITorneosParticipaciones>) => {
          if (torneosParticipaciones.body) {
            return of(torneosParticipaciones.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default torneosParticipacionesResolve;
