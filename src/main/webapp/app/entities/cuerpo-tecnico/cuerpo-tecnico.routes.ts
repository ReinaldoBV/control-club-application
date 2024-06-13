import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CuerpoTecnicoComponent } from './list/cuerpo-tecnico.component';
import { CuerpoTecnicoDetailComponent } from './detail/cuerpo-tecnico-detail.component';
import { CuerpoTecnicoUpdateComponent } from './update/cuerpo-tecnico-update.component';
import CuerpoTecnicoResolve from './route/cuerpo-tecnico-routing-resolve.service';

const cuerpoTecnicoRoute: Routes = [
  {
    path: '',
    component: CuerpoTecnicoComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CuerpoTecnicoDetailComponent,
    resolve: {
      cuerpoTecnico: CuerpoTecnicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CuerpoTecnicoUpdateComponent,
    resolve: {
      cuerpoTecnico: CuerpoTecnicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CuerpoTecnicoUpdateComponent,
    resolve: {
      cuerpoTecnico: CuerpoTecnicoResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default cuerpoTecnicoRoute;
