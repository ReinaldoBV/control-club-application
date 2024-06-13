import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPadre } from '../padre.model';
import { PadreService } from '../service/padre.service';

const padreResolve = (route: ActivatedRouteSnapshot): Observable<null | IPadre> => {
  const id = route.params['id'];
  if (id) {
    return inject(PadreService)
      .find(id)
      .pipe(
        mergeMap((padre: HttpResponse<IPadre>) => {
          if (padre.body) {
            return of(padre.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default padreResolve;
