import dayjs from 'dayjs/esm';
import { TipoPago } from 'app/entities/enumerations/tipo-pago.model';

export interface IFinanzasIngreso {
  id: number;
  tipo?: keyof typeof TipoPago | null;
  descripcion?: string | null;
  monto?: number | null;
  fecha?: dayjs.Dayjs | null;
}

export type NewFinanzasIngreso = Omit<IFinanzasIngreso, 'id'> & { id: null };
