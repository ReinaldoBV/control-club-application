import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDirectivos } from '../directivos.model';
import { DirectivosService } from '../service/directivos.service';

const directivosResolve = (route: ActivatedRouteSnapshot): Observable<null | IDirectivos> => {
  const id = route.params['id'];
  if (id) {
    return inject(DirectivosService)
      .find(id)
      .pipe(
        mergeMap((directivos: HttpResponse<IDirectivos>) => {
          if (directivos.body) {
            return of(directivos.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default directivosResolve;
