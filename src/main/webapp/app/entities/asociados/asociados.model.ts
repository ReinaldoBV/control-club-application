import dayjs from 'dayjs/esm';

export interface IAsociados {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  cargo?: string | null;
  telefono?: string | null;
  fechaAsoc?: dayjs.Dayjs | null;
  email?: string | null;
}

export type NewAsociados = Omit<IAsociados, 'id'> & { id: null };
