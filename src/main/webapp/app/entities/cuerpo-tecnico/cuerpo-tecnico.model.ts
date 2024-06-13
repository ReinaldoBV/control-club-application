import dayjs from 'dayjs/esm';
import { IAsociados } from 'app/entities/asociados/asociados.model';

export interface ICuerpoTecnico {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  rolTecnico?: string | null;
  telefono?: string | null;
  fechaInicio?: dayjs.Dayjs | null;
  email?: string | null;
  asociados?: Pick<IAsociados, 'id'> | null;
}

export type NewCuerpoTecnico = Omit<ICuerpoTecnico, 'id'> & { id: null };
