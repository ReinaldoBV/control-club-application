import { IJugador } from 'app/entities/jugador/jugador.model';

export interface ICategorias {
  id: number;
  anioInicio?: number | null;
  anioFinal?: number | null;
  nombreCategoria?: string | null;
  jugador?: Pick<IJugador, 'id'> | null;
}

export type NewCategorias = Omit<ICategorias, 'id'> & { id: null };
