import dayjs from 'dayjs/esm';

export interface ICuerpoTecnico {
  id: number;
  nombres?: string | null;
  apellidos?: string | null;
  rolTecnico?: string | null;
  telefono?: string | null;
  fechaInicio?: dayjs.Dayjs | null;
  email?: string | null;
}

export type NewCuerpoTecnico = Omit<ICuerpoTecnico, 'id'> & { id: null };
