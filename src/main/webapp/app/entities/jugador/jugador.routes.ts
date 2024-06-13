import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { JugadorComponent } from './list/jugador.component';
import { JugadorDetailComponent } from './detail/jugador-detail.component';
import { JugadorUpdateComponent } from './update/jugador-update.component';
import JugadorResolve from './route/jugador-routing-resolve.service';

const jugadorRoute: Routes = [
  {
    path: '',
    component: JugadorComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: JugadorDetailComponent,
    resolve: {
      jugador: JugadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: JugadorUpdateComponent,
    resolve: {
      jugador: JugadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: JugadorUpdateComponent,
    resolve: {
      jugador: JugadorResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default jugadorRoute;
