import dayjs from 'dayjs/esm';

export interface IPartido {
  id: number;
  fecha?: dayjs.Dayjs | null;
  oponente?: string | null;
  resultado?: string | null;
}

export type NewPartido = Omit<IPartido, 'id'> & { id: null };
