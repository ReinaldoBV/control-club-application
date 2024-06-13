import dayjs from 'dayjs/esm';
import { TipoIngreso } from 'app/entities/enumerations/tipo-ingreso.model';

export interface IFinanzasIngreso {
  id: number;
  tipo?: keyof typeof TipoIngreso | null;
  descripcion?: string | null;
  monto?: number | null;
  fecha?: dayjs.Dayjs | null;
}

export type NewFinanzasIngreso = Omit<IFinanzasIngreso, 'id'> & { id: null };
