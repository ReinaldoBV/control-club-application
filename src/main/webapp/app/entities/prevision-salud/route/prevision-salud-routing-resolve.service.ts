import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrevisionSalud } from '../prevision-salud.model';
import { PrevisionSaludService } from '../service/prevision-salud.service';

const previsionSaludResolve = (route: ActivatedRouteSnapshot): Observable<null | IPrevisionSalud> => {
  const id = route.params['id'];
  if (id) {
    return inject(PrevisionSaludService)
      .find(id)
      .pipe(
        mergeMap((previsionSalud: HttpResponse<IPrevisionSalud>) => {
          if (previsionSalud.body) {
            return of(previsionSalud.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default previsionSaludResolve;
