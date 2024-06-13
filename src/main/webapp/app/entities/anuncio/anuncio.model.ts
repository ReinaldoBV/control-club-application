import dayjs from 'dayjs/esm';

export interface IAnuncio {
  id: number;
  titulo?: string | null;
  contenido?: string | null;
  fechaPublicacion?: dayjs.Dayjs | null;
}

export type NewAnuncio = Omit<IAnuncio, 'id'> & { id: null };
