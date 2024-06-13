import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PadreComponent } from './list/padre.component';
import { PadreDetailComponent } from './detail/padre-detail.component';
import { PadreUpdateComponent } from './update/padre-update.component';
import PadreResolve from './route/padre-routing-resolve.service';

const padreRoute: Routes = [
  {
    path: '',
    component: PadreComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PadreDetailComponent,
    resolve: {
      padre: PadreResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PadreUpdateComponent,
    resolve: {
      padre: PadreResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PadreUpdateComponent,
    resolve: {
      padre: PadreResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default padreRoute;
