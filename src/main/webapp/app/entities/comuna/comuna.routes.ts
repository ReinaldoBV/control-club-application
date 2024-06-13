import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ComunaComponent } from './list/comuna.component';
import { ComunaDetailComponent } from './detail/comuna-detail.component';
import { ComunaUpdateComponent } from './update/comuna-update.component';
import ComunaResolve from './route/comuna-routing-resolve.service';

const comunaRoute: Routes = [
  {
    path: '',
    component: ComunaComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ComunaDetailComponent,
    resolve: {
      comuna: ComunaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ComunaUpdateComponent,
    resolve: {
      comuna: ComunaResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default comunaRoute;
