import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { BienesComponent } from './list/bienes.component';
import { BienesDetailComponent } from './detail/bienes-detail.component';
import { BienesUpdateComponent } from './update/bienes-update.component';
import BienesResolve from './route/bienes-routing-resolve.service';

const bienesRoute: Routes = [
  {
    path: '',
    component: BienesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BienesDetailComponent,
    resolve: {
      bienes: BienesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BienesUpdateComponent,
    resolve: {
      bienes: BienesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BienesUpdateComponent,
    resolve: {
      bienes: BienesResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default bienesRoute;
