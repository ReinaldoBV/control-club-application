import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IJugador } from '../jugador.model';
import { JugadorService } from '../service/jugador.service';

const jugadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IJugador> => {
  const id = route.params['id'];
  if (id) {
    return inject(JugadorService)
      .find(id)
      .pipe(
        mergeMap((jugador: HttpResponse<IJugador>) => {
          if (jugador.body) {
            return of(jugador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default jugadorResolve;
