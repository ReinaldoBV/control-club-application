import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { TorneosParticipacionesComponent } from './list/torneos-participaciones.component';
import { TorneosParticipacionesDetailComponent } from './detail/torneos-participaciones-detail.component';
import { TorneosParticipacionesUpdateComponent } from './update/torneos-participaciones-update.component';
import TorneosParticipacionesResolve from './route/torneos-participaciones-routing-resolve.service';

const torneosParticipacionesRoute: Routes = [
  {
    path: '',
    component: TorneosParticipacionesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TorneosParticipacionesDetailComponent,
    resolve: {
      torneosParticipaciones: TorneosParticipacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TorneosParticipacionesUpdateComponent,
    resolve: {
      torneosParticipaciones: TorneosParticipacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TorneosParticipacionesUpdateComponent,
    resolve: {
      torneosParticipaciones: TorneosParticipacionesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default torneosParticipacionesRoute;
