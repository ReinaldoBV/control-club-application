import dayjs from 'dayjs/esm';

export interface IClub {
  id: number;
  razon?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  email?: string | null;
  fechaFundacion?: dayjs.Dayjs | null;
  presidente?: string | null;
}

export type NewClub = Omit<IClub, 'id'> & { id: null };
