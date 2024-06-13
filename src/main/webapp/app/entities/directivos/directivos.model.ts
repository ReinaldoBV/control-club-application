import dayjs from 'dayjs/esm';
import { IAsociados } from 'app/entities/asociados/asociados.model';

export interface IDirectivos {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  cargo?: string | null;
  telefono?: string | null;
  fechaEleccion?: dayjs.Dayjs | null;
  fechaVencimiento?: dayjs.Dayjs | null;
  email?: string | null;
  asociados?: Pick<IAsociados, 'id'> | null;
}

export type NewDirectivos = Omit<IDirectivos, 'id'> & { id: null };
