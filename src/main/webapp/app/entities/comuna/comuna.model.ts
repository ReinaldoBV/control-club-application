export interface IComuna {
  id: number;
  comuna?: string | null;
}

export type NewComuna = Omit<IComuna, 'id'> & { id: null };
