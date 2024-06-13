import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IBienes {
  id: number;
  descripcion?: string | null;
  cantidad?: number | null;
  estado?: string | null;
  asignadoA?: Pick<IJugador, 'id'> | null;
}

export type NewBienes = Omit<IBienes, 'id'> & { id: null };
