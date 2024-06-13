export interface ICentroSalud {
  id: number;
  centroSalud?: string | null;
}

export type NewCentroSalud = Omit<ICentroSalud, 'id'> & { id: null };
