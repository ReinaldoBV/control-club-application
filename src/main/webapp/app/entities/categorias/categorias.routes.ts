import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CategoriasComponent } from './list/categorias.component';
import { CategoriasDetailComponent } from './detail/categorias-detail.component';
import { CategoriasUpdateComponent } from './update/categorias-update.component';
import CategoriasResolve from './route/categorias-routing-resolve.service';

const categoriasRoute: Routes = [
  {
    path: '',
    component: CategoriasComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CategoriasDetailComponent,
    resolve: {
      categorias: CategoriasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CategoriasUpdateComponent,
    resolve: {
      categorias: CategoriasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CategoriasUpdateComponent,
    resolve: {
      categorias: CategoriasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default categoriasRoute;
