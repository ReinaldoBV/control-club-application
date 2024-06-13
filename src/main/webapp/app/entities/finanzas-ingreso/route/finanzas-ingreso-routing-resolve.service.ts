import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFinanzasIngreso } from '../finanzas-ingreso.model';
import { FinanzasIngresoService } from '../service/finanzas-ingreso.service';

const finanzasIngresoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFinanzasIngreso> => {
  const id = route.params['id'];
  if (id) {
    return inject(FinanzasIngresoService)
      .find(id)
      .pipe(
        mergeMap((finanzasIngreso: HttpResponse<IFinanzasIngreso>) => {
          if (finanzasIngreso.body) {
            return of(finanzasIngreso.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default finanzasIngresoResolve;
