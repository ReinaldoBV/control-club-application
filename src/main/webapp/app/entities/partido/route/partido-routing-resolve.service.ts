import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPartido } from '../partido.model';
import { PartidoService } from '../service/partido.service';

const partidoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPartido> => {
  const id = route.params['id'];
  if (id) {
    return inject(PartidoService)
      .find(id)
      .pipe(
        mergeMap((partido: HttpResponse<IPartido>) => {
          if (partido.body) {
            return of(partido.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default partidoResolve;
