import dayjs from 'dayjs/esm';
import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';

export interface IAsociados {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  cargo?: string | null;
  telefono?: string | null;
  fechaAsoc?: dayjs.Dayjs | null;
  email?: string | null;
  directivos?: Pick<IDirectivos, 'id'> | null;
  cuerpoTecnico?: Pick<ICuerpoTecnico, 'id'> | null;
}

export type NewAsociados = Omit<IAsociados, 'id'> & { id: null };
