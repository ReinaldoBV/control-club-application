import dayjs from 'dayjs/esm';
import { TipoEgreso } from 'app/entities/enumerations/tipo-egreso.model';

export interface IFinanzasEgreso {
  id: number;
  tipo?: keyof typeof TipoEgreso | null;
  descripcion?: string | null;
  monto?: number | null;
  fecha?: dayjs.Dayjs | null;
}

export type NewFinanzasEgreso = Omit<IFinanzasEgreso, 'id'> & { id: null };
