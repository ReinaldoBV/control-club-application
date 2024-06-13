import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICentroEducativo } from '../centro-educativo.model';
import { CentroEducativoService } from '../service/centro-educativo.service';

const centroEducativoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICentroEducativo> => {
  const id = route.params['id'];
  if (id) {
    return inject(CentroEducativoService)
      .find(id)
      .pipe(
        mergeMap((centroEducativo: HttpResponse<ICentroEducativo>) => {
          if (centroEducativo.body) {
            return of(centroEducativo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default centroEducativoResolve;
