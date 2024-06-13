import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'controlClubApplicationApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'club',
    data: { pageTitle: 'controlClubApplicationApp.club.home.title' },
    loadChildren: () => import('./club/club.routes'),
  },
  {
    path: 'comuna',
    data: { pageTitle: 'controlClubApplicationApp.comuna.home.title' },
    loadChildren: () => import('./comuna/comuna.routes'),
  },
  {
    path: 'centro-salud',
    data: { pageTitle: 'controlClubApplicationApp.centroSalud.home.title' },
    loadChildren: () => import('./centro-salud/centro-salud.routes'),
  },
  {
    path: 'centro-educativo',
    data: { pageTitle: 'controlClubApplicationApp.centroEducativo.home.title' },
    loadChildren: () => import('./centro-educativo/centro-educativo.routes'),
  },
  {
    path: 'categorias',
    data: { pageTitle: 'controlClubApplicationApp.categorias.home.title' },
    loadChildren: () => import('./categorias/categorias.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
