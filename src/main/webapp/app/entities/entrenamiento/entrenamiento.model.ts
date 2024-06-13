import dayjs from 'dayjs/esm';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';

export interface IEntrenamiento {
  id: number;
  fechaHora?: dayjs.Dayjs | null;
  duracion?: number | null;
  cuerpoTecnico?: Pick<ICuerpoTecnico, 'id'> | null;
}

export type NewEntrenamiento = Omit<IEntrenamiento, 'id'> & { id: null };
