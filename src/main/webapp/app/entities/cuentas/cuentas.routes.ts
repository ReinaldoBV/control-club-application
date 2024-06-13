import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CuentasComponent } from './list/cuentas.component';
import { CuentasDetailComponent } from './detail/cuentas-detail.component';
import { CuentasUpdateComponent } from './update/cuentas-update.component';
import CuentasResolve from './route/cuentas-routing-resolve.service';

const cuentasRoute: Routes = [
  {
    path: '',
    component: CuentasComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CuentasDetailComponent,
    resolve: {
      cuentas: CuentasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CuentasUpdateComponent,
    resolve: {
      cuentas: CuentasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CuentasUpdateComponent,
    resolve: {
      cuentas: CuentasResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cuentasRoute;
