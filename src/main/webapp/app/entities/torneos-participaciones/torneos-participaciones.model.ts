import dayjs from 'dayjs/esm';

export interface ITorneosParticipaciones {
  id: number;
  descripcion?: string | null;
  monto?: number | null;
  fecha?: dayjs.Dayjs | null;
}

export type NewTorneosParticipaciones = Omit<ITorneosParticipaciones, 'id'> & { id: null };
