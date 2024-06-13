import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { DirectivosComponent } from './list/directivos.component';
import { DirectivosDetailComponent } from './detail/directivos-detail.component';
import { DirectivosUpdateComponent } from './update/directivos-update.component';
import DirectivosResolve from './route/directivos-routing-resolve.service';

const directivosRoute: Routes = [
  {
    path: '',
    component: DirectivosComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DirectivosDetailComponent,
    resolve: {
      directivos: DirectivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DirectivosUpdateComponent,
    resolve: {
      directivos: DirectivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DirectivosUpdateComponent,
    resolve: {
      directivos: DirectivosResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default directivosRoute;
