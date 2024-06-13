import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICuentas } from '../cuentas.model';
import { CuentasService } from '../service/cuentas.service';

const cuentasResolve = (route: ActivatedRouteSnapshot): Observable<null | ICuentas> => {
  const id = route.params['id'];
  if (id) {
    return inject(CuentasService)
      .find(id)
      .pipe(
        mergeMap((cuentas: HttpResponse<ICuentas>) => {
          if (cuentas.body) {
            return of(cuentas.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cuentasResolve;
