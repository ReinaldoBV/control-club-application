import { IComuna } from 'app/entities/comuna/comuna.model';

export interface ICentroSalud {
  id: number;
  centroSalud?: string | null;
  comuna?: Pick<IComuna, 'id'> | null;
}

export type NewCentroSalud = Omit<ICentroSalud, 'id'> & { id: null };
