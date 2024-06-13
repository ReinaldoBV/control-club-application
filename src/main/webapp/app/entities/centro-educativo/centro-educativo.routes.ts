import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CentroEducativoComponent } from './list/centro-educativo.component';
import { CentroEducativoDetailComponent } from './detail/centro-educativo-detail.component';
import { CentroEducativoUpdateComponent } from './update/centro-educativo-update.component';
import CentroEducativoResolve from './route/centro-educativo-routing-resolve.service';

const centroEducativoRoute: Routes = [
  {
    path: '',
    component: CentroEducativoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CentroEducativoDetailComponent,
    resolve: {
      centroEducativo: CentroEducativoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CentroEducativoUpdateComponent,
    resolve: {
      centroEducativo: CentroEducativoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CentroEducativoUpdateComponent,
    resolve: {
      centroEducativo: CentroEducativoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default centroEducativoRoute;
