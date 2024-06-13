import dayjs from 'dayjs/esm';
import { IJugador } from 'app/entities/jugador/jugador.model';
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
  jugador?: Pick<IJugador, 'id'> | null;
}

export type NewCuentas = Omit<ICuentas, 'id'> & { id: null };
