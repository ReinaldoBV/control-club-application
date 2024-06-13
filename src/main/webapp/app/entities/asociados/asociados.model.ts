import dayjs from 'dayjs/esm';
import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IAsociados {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  cargo?: string | null;
  telefono?: string | null;
  fechaAsoc?: dayjs.Dayjs | null;
  email?: string | null;
  jugador?: Pick<IJugador, 'id'> | null;
}

export type NewAsociados = Omit<IAsociados, 'id'> & { id: null };
