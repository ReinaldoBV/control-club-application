import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAsistencia } from '../asistencia.model';
import { AsistenciaService } from '../service/asistencia.service';

const asistenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAsistencia> => {
  const id = route.params['id'];
  if (id) {
    return inject(AsistenciaService)
      .find(id)
      .pipe(
        mergeMap((asistencia: HttpResponse<IAsistencia>) => {
          if (asistencia.body) {
            return of(asistencia.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default asistenciaResolve;
