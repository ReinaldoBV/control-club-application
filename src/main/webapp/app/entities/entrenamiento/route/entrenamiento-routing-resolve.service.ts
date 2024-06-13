import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntrenamiento } from '../entrenamiento.model';
import { EntrenamientoService } from '../service/entrenamiento.service';

const entrenamientoResolve = (route: ActivatedRouteSnapshot): Observable<null | IEntrenamiento> => {
  const id = route.params['id'];
  if (id) {
    return inject(EntrenamientoService)
      .find(id)
      .pipe(
        mergeMap((entrenamiento: HttpResponse<IEntrenamiento>) => {
          if (entrenamiento.body) {
            return of(entrenamiento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default entrenamientoResolve;
