import dayjs from 'dayjs/esm';
import { TipoCuenta } from 'app/entities/enumerations/tipo-cuenta.model';
import { EstadoCuenta } from 'app/entities/enumerations/estado-cuenta.model';

export interface ICuentas {
  id: number;
  tipo?: keyof typeof TipoCuenta | null;
  descripcion?: string | null;
  monto?: number | null;
  nroCuotas?: number | null;
  fechaVencimiento?: dayjs.Dayjs | null;
  estado?: keyof typeof EstadoCuenta | null;
}

export type NewCuentas = Omit<ICuentas, 'id'> & { id: null };
