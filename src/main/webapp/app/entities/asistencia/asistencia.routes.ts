import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { AsistenciaComponent } from './list/asistencia.component';
import { AsistenciaDetailComponent } from './detail/asistencia-detail.component';
import { AsistenciaUpdateComponent } from './update/asistencia-update.component';
import AsistenciaResolve from './route/asistencia-routing-resolve.service';

const asistenciaRoute: Routes = [
  {
    path: '',
    component: AsistenciaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AsistenciaDetailComponent,
    resolve: {
      asistencia: AsistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AsistenciaUpdateComponent,
    resolve: {
      asistencia: AsistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AsistenciaUpdateComponent,
    resolve: {
      asistencia: AsistenciaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default asistenciaRoute;
