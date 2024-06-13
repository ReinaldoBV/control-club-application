import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PartidoComponent } from './list/partido.component';
import { PartidoDetailComponent } from './detail/partido-detail.component';
import { PartidoUpdateComponent } from './update/partido-update.component';
import PartidoResolve from './route/partido-routing-resolve.service';

const partidoRoute: Routes = [
  {
    path: '',
    component: PartidoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartidoDetailComponent,
    resolve: {
      partido: PartidoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartidoUpdateComponent,
    resolve: {
      partido: PartidoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartidoUpdateComponent,
    resolve: {
      partido: PartidoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default partidoRoute;
