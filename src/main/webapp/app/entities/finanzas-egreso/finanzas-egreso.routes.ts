import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { FinanzasEgresoComponent } from './list/finanzas-egreso.component';
import { FinanzasEgresoDetailComponent } from './detail/finanzas-egreso-detail.component';
import { FinanzasEgresoUpdateComponent } from './update/finanzas-egreso-update.component';
import FinanzasEgresoResolve from './route/finanzas-egreso-routing-resolve.service';

const finanzasEgresoRoute: Routes = [
  {
    path: '',
    component: FinanzasEgresoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FinanzasEgresoDetailComponent,
    resolve: {
      finanzasEgreso: FinanzasEgresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FinanzasEgresoUpdateComponent,
    resolve: {
      finanzasEgreso: FinanzasEgresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FinanzasEgresoUpdateComponent,
    resolve: {
      finanzasEgreso: FinanzasEgresoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default finanzasEgresoRoute;
