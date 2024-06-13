import dayjs from 'dayjs/esm';

export interface IDirectivos {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  cargo?: string | null;
  telefono?: string | null;
  fechaEleccion?: dayjs.Dayjs | null;
  fechaVencimiento?: dayjs.Dayjs | null;
  email?: string | null;
}

export type NewDirectivos = Omit<IDirectivos, 'id'> & { id: null };
