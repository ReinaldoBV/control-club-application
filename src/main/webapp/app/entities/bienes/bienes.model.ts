export interface IBienes {
  id: number;
  descripcion?: string | null;
  cantidad?: number | null;
  estado?: string | null;
}

export type NewBienes = Omit<IBienes, 'id'> & { id: null };
