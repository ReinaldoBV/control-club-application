import dayjs from 'dayjs/esm';
import { TipoAsistencia } from 'app/entities/enumerations/tipo-asistencia.model';

export interface IAsistencia {
  id: number;
  tipo?: keyof typeof TipoAsistencia | null;
  idEvento?: number | null;
  fecha?: dayjs.Dayjs | null;
  asistencia?: boolean | null;
}

export type NewAsistencia = Omit<IAsistencia, 'id'> & { id: null };
