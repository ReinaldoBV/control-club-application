import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';
import { EstadisticasBaloncestoService } from '../service/estadisticas-baloncesto.service';

const estadisticasBaloncestoResolve = (route: ActivatedRouteSnapshot): Observable<null | IEstadisticasBaloncesto> => {
  const id = route.params['id'];
  if (id) {
    return inject(EstadisticasBaloncestoService)
      .find(id)
      .pipe(
        mergeMap((estadisticasBaloncesto: HttpResponse<IEstadisticasBaloncesto>) => {
          if (estadisticasBaloncesto.body) {
            return of(estadisticasBaloncesto.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default estadisticasBaloncestoResolve;
