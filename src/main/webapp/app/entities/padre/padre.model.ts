export interface IPadre {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  relacion?: string | null;
  telefono?: string | null;
  asociado?: boolean | null;
  email?: string | null;
}

export type NewPadre = Omit<IPadre, 'id'> & { id: null };
