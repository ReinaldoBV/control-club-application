export interface IEstadisticasBaloncesto {
  id: number;
  puntos?: number | null;
  rebotes?: number | null;
  asistencias?: number | null;
  robos?: number | null;
  bloqueos?: number | null;
  porcentajeTiro?: number | null;
}

export type NewEstadisticasBaloncesto = Omit<IEstadisticasBaloncesto, 'id'> & { id: null };
