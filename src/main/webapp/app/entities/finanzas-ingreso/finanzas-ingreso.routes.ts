import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FinanzasIngresoComponent } from './list/finanzas-ingreso.component';
import { FinanzasIngresoDetailComponent } from './detail/finanzas-ingreso-detail.component';
import { FinanzasIngresoUpdateComponent } from './update/finanzas-ingreso-update.component';
import FinanzasIngresoResolve from './route/finanzas-ingreso-routing-resolve.service';

const finanzasIngresoRoute: Routes = [
  {
    path: '',
    component: FinanzasIngresoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FinanzasIngresoDetailComponent,
    resolve: {
      finanzasIngreso: FinanzasIngresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FinanzasIngresoUpdateComponent,
    resolve: {
      finanzasIngreso: FinanzasIngresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FinanzasIngresoUpdateComponent,
    resolve: {
      finanzasIngreso: FinanzasIngresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default finanzasIngresoRoute;
