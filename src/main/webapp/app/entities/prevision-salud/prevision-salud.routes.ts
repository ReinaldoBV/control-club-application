import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PrevisionSaludComponent } from './list/prevision-salud.component';
import { PrevisionSaludDetailComponent } from './detail/prevision-salud-detail.component';
import { PrevisionSaludUpdateComponent } from './update/prevision-salud-update.component';
import PrevisionSaludResolve from './route/prevision-salud-routing-resolve.service';

const previsionSaludRoute: Routes = [
  {
    path: '',
    component: PrevisionSaludComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PrevisionSaludDetailComponent,
    resolve: {
      previsionSalud: PrevisionSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PrevisionSaludUpdateComponent,
    resolve: {
      previsionSalud: PrevisionSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PrevisionSaludUpdateComponent,
    resolve: {
      previsionSalud: PrevisionSaludResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default previsionSaludRoute;
