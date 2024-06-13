import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { CuerpoTecnicoService } from '../service/cuerpo-tecnico.service';

const cuerpoTecnicoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICuerpoTecnico> => {
  const id = route.params['id'];
  if (id) {
    return inject(CuerpoTecnicoService)
      .find(id)
      .pipe(
        mergeMap((cuerpoTecnico: HttpResponse<ICuerpoTecnico>) => {
          if (cuerpoTecnico.body) {
            return of(cuerpoTecnico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cuerpoTecnicoResolve;
