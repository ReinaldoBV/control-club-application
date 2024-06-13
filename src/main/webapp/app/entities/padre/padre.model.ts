import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IPadre {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  relacion?: string | null;
  telefono?: string | null;
  asociado?: boolean | null;
  email?: string | null;
  jugador?: Pick<IJugador, 'id'> | null;
}

export type NewPadre = Omit<IPadre, 'id'> & { id: null };
