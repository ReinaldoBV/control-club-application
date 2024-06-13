import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { ClubComponent } from './list/club.component';
import { ClubDetailComponent } from './detail/club-detail.component';
import { ClubUpdateComponent } from './update/club-update.component';
import ClubResolve from './route/club-routing-resolve.service';

const clubRoute: Routes = [
  {
    path: '',
    component: ClubComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ClubDetailComponent,
    resolve: {
      club: ClubResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ClubUpdateComponent,
    resolve: {
      club: ClubResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ClubUpdateComponent,
    resolve: {
      club: ClubResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default clubRoute;
