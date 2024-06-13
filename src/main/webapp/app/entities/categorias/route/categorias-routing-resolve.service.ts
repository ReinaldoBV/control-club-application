import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICategorias } from '../categorias.model';
import { CategoriasService } from '../service/categorias.service';

const categoriasResolve = (route: ActivatedRouteSnapshot): Observable<null | ICategorias> => {
  const id = route.params['id'];
  if (id) {
    return inject(CategoriasService)
      .find(id)
      .pipe(
        mergeMap((categorias: HttpResponse<ICategorias>) => {
          if (categorias.body) {
            return of(categorias.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default categoriasResolve;
