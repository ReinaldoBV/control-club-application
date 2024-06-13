import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IComuna } from '../comuna.model';
import { ComunaService } from '../service/comuna.service';

const comunaResolve = (route: ActivatedRouteSnapshot): Observable<null | IComuna> => {
  const id = route.params['id'];
  if (id) {
    return inject(ComunaService)
      .find(id)
      .pipe(
        mergeMap((comuna: HttpResponse<IComuna>) => {
          if (comuna.body) {
            return of(comuna.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default comunaResolve;
