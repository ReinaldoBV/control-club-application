import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EstadisticasBaloncestoComponent } from './list/estadisticas-baloncesto.component';
import { EstadisticasBaloncestoDetailComponent } from './detail/estadisticas-baloncesto-detail.component';
import { EstadisticasBaloncestoUpdateComponent } from './update/estadisticas-baloncesto-update.component';
import EstadisticasBaloncestoResolve from './route/estadisticas-baloncesto-routing-resolve.service';

const estadisticasBaloncestoRoute: Routes = [
  {
    path: '',
    component: EstadisticasBaloncestoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EstadisticasBaloncestoDetailComponent,
    resolve: {
      estadisticasBaloncesto: EstadisticasBaloncestoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EstadisticasBaloncestoUpdateComponent,
    resolve: {
      estadisticasBaloncesto: EstadisticasBaloncestoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EstadisticasBaloncestoUpdateComponent,
    resolve: {
      estadisticasBaloncesto: EstadisticasBaloncestoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default estadisticasBaloncestoRoute;
