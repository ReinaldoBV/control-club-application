import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AsociadosComponent } from './list/asociados.component';
import { AsociadosDetailComponent } from './detail/asociados-detail.component';
import { AsociadosUpdateComponent } from './update/asociados-update.component';
import AsociadosResolve from './route/asociados-routing-resolve.service';

const asociadosRoute: Routes = [
  {
    path: '',
    component: AsociadosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AsociadosDetailComponent,
    resolve: {
      asociados: AsociadosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AsociadosUpdateComponent,
    resolve: {
      asociados: AsociadosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AsociadosUpdateComponent,
    resolve: {
      asociados: AsociadosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default asociadosRoute;
