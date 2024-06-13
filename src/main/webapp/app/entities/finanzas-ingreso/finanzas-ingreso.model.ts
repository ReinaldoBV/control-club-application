import dayjs from 'dayjs/esm';
import { IJugador } from 'app/entities/jugador/jugador.model';
import { TipoIngreso } from 'app/entities/enumerations/tipo-ingreso.model';

export interface IFinanzasIngreso {
  id: number;
  tipo?: keyof typeof TipoIngreso | null;
  descripcion?: string | null;
  monto?: number | null;
  fecha?: dayjs.Dayjs | null;
  jugador?: Pick<IJugador, 'id'> | null;
}

export type NewFinanzasIngreso = Omit<IFinanzasIngreso, 'id'> & { id: null };
