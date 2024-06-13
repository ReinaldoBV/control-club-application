import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 5699,
  razon: 'violence aw dribble',
  direccion: 'for fantasize jagged',
  telefono: 'with',
  email: 'Enrique21@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'vide boring ugh',
};

export const sampleWithPartialData: IClub = {
  id: 18894,
  razon: 'wherever tedious snorkel',
  direccion: 'out generally for',
  telefono: 'upside-down periodic phew',
  email: 'Diego_VelezTamayo30@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'restate ripe from',
};

export const sampleWithFullData: IClub = {
  id: 27020,
  razon: 'flight concerning',
  direccion: 'fraternise',
  telefono: 'phooey gadzooks calmly',
  email: 'Anita_ArmijoDelacruz@yahoo.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'demolish overconfidently knowing',
};

export const sampleWithNewData: NewClub = {
  razon: 'foolhardy memorable oldie',
  direccion: 'whenever cluster besides',
  telefono: 'instead',
  email: 'Hernan48@hotmail.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'provided',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
