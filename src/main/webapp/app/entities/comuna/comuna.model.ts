import { RMComuna } from 'app/entities/enumerations/rm-comuna.model';

export interface IComuna {
  id: number;
  comuna?: keyof typeof RMComuna | null;
}

export type NewComuna = Omit<IComuna, 'id'> & { id: null };
