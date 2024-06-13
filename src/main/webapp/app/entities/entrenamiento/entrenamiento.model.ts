import dayjs from 'dayjs/esm';

export interface IEntrenamiento {
  id: number;
  fechaHora?: dayjs.Dayjs | null;
  duracion?: number | null;
}

export type NewEntrenamiento = Omit<IEntrenamiento, 'id'> & { id: null };
