import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CentroSaludComponent } from './list/centro-salud.component';
import { CentroSaludDetailComponent } from './detail/centro-salud-detail.component';
import { CentroSaludUpdateComponent } from './update/centro-salud-update.component';
import CentroSaludResolve from './route/centro-salud-routing-resolve.service';

const centroSaludRoute: Routes = [
  {
    path: '',
    component: CentroSaludComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CentroSaludDetailComponent,
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CentroSaludUpdateComponent,
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CentroSaludUpdateComponent,
    resolve: {
      centroSalud: CentroSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centroSaludRoute;
