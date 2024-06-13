import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBienes } from '../bienes.model';
import { BienesService } from '../service/bienes.service';

const bienesResolve = (route: ActivatedRouteSnapshot): Observable<null | IBienes> => {
  const id = route.params['id'];
  if (id) {
    return inject(BienesService)
      .find(id)
      .pipe(
        mergeMap((bienes: HttpResponse<IBienes>) => {
          if (bienes.body) {
            return of(bienes.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bienesResolve;
