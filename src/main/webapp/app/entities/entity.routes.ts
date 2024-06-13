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
  {
    path: 'directivos',
    data: { pageTitle: 'controlClubApplicationApp.directivos.home.title' },
    loadChildren: () => import('./directivos/directivos.routes'),
  },
  {
    path: 'asociados',
    data: { pageTitle: 'controlClubApplicationApp.asociados.home.title' },
    loadChildren: () => import('./asociados/asociados.routes'),
  },
  {
    path: 'padre',
    data: { pageTitle: 'controlClubApplicationApp.padre.home.title' },
    loadChildren: () => import('./padre/padre.routes'),
  },
  {
    path: 'torneos-participaciones',
    data: { pageTitle: 'controlClubApplicationApp.torneosParticipaciones.home.title' },
    loadChildren: () => import('./torneos-participaciones/torneos-participaciones.routes'),
  },
  {
    path: 'estadisticas-baloncesto',
    data: { pageTitle: 'controlClubApplicationApp.estadisticasBaloncesto.home.title' },
    loadChildren: () => import('./estadisticas-baloncesto/estadisticas-baloncesto.routes'),
  },
  {
    path: 'partido',
    data: { pageTitle: 'controlClubApplicationApp.partido.home.title' },
    loadChildren: () => import('./partido/partido.routes'),
  },
  {
    path: 'jugador',
    data: { pageTitle: 'controlClubApplicationApp.jugador.home.title' },
    loadChildren: () => import('./jugador/jugador.routes'),
  },
  {
    path: 'prevision-salud',
    data: { pageTitle: 'controlClubApplicationApp.previsionSalud.home.title' },
    loadChildren: () => import('./prevision-salud/prevision-salud.routes'),
  },
  {
    path: 'cuerpo-tecnico',
    data: { pageTitle: 'controlClubApplicationApp.cuerpoTecnico.home.title' },
    loadChildren: () => import('./cuerpo-tecnico/cuerpo-tecnico.routes'),
  },
  {
    path: 'finanzas-ingreso',
    data: { pageTitle: 'controlClubApplicationApp.finanzasIngreso.home.title' },
    loadChildren: () => import('./finanzas-ingreso/finanzas-ingreso.routes'),
  },
  {
    path: 'finanzas-egreso',
    data: { pageTitle: 'controlClubApplicationApp.finanzasEgreso.home.title' },
    loadChildren: () => import('./finanzas-egreso/finanzas-egreso.routes'),
  },
  {
    path: 'cuentas',
    data: { pageTitle: 'controlClubApplicationApp.cuentas.home.title' },
    loadChildren: () => import('./cuentas/cuentas.routes'),
  },
  {
    path: 'entrenamiento',
    data: { pageTitle: 'controlClubApplicationApp.entrenamiento.home.title' },
    loadChildren: () => import('./entrenamiento/entrenamiento.routes'),
  },
  {
    path: 'mensaje',
    data: { pageTitle: 'controlClubApplicationApp.mensaje.home.title' },
    loadChildren: () => import('./mensaje/mensaje.routes'),
  },
  {
    path: 'anuncio',
    data: { pageTitle: 'controlClubApplicationApp.anuncio.home.title' },
    loadChildren: () => import('./anuncio/anuncio.routes'),
  },
  {
    path: 'bienes',
    data: { pageTitle: 'controlClubApplicationApp.bienes.home.title' },
    loadChildren: () => import('./bienes/bienes.routes'),
  },
  {
    path: 'usuario',
    data: { pageTitle: 'controlClubApplicationApp.usuario.home.title' },
    loadChildren: () => import('./usuario/usuario.routes'),
  },
  {
    path: 'asistencia',
    data: { pageTitle: 'controlClubApplicationApp.asistencia.home.title' },
    loadChildren: () => import('./asistencia/asistencia.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
