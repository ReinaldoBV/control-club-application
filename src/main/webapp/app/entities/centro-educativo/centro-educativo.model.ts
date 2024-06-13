export interface ICentroEducativo {
  id: number;
  centroEducativo?: string | null;
}

export type NewCentroEducativo = Omit<ICentroEducativo, 'id'> & { id: null };
