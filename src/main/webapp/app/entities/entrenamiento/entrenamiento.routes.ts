import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EntrenamientoComponent } from './list/entrenamiento.component';
import { EntrenamientoDetailComponent } from './detail/entrenamiento-detail.component';
import { EntrenamientoUpdateComponent } from './update/entrenamiento-update.component';
import EntrenamientoResolve from './route/entrenamiento-routing-resolve.service';

const entrenamientoRoute: Routes = [
  {
    path: '',
    component: EntrenamientoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntrenamientoDetailComponent,
    resolve: {
      entrenamiento: EntrenamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntrenamientoUpdateComponent,
    resolve: {
      entrenamiento: EntrenamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntrenamientoUpdateComponent,
    resolve: {
      entrenamiento: EntrenamientoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default entrenamientoRoute;
