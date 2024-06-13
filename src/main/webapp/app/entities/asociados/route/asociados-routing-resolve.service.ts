import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAsociados } from '../asociados.model';
import { AsociadosService } from '../service/asociados.service';

const asociadosResolve = (route: ActivatedRouteSnapshot): Observable<null | IAsociados> => {
  const id = route.params['id'];
  if (id) {
    return inject(AsociadosService)
      .find(id)
      .pipe(
        mergeMap((asociados: HttpResponse<IAsociados>) => {
          if (asociados.body) {
            return of(asociados.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default asociadosResolve;
