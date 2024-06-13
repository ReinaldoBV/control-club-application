import { IComuna } from 'app/entities/comuna/comuna.model';

export interface ICentroEducativo {
  id: number;
  centroEducativo?: string | null;
  comuna?: Pick<IComuna, 'id'> | null;
}

export type NewCentroEducativo = Omit<ICentroEducativo, 'id'> & { id: null };
