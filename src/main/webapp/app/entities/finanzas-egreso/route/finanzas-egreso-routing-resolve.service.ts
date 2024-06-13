import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';

const finanzasEgresoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFinanzasEgreso> => {
  const id = route.params['id'];
  if (id) {
    return inject(FinanzasEgresoService)
      .find(id)
      .pipe(
        mergeMap((finanzasEgreso: HttpResponse<IFinanzasEgreso>) => {
          if (finanzasEgreso.body) {
            return of(finanzasEgreso.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default finanzasEgresoResolve;
