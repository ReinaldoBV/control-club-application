import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 19672,
  razon: 'platypus slur',
  direccion: 'towards upholster',
  telefono: 'handicap',
  email: 'Octavio_MezaMaya@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'acidic',
};

export const sampleWithPartialData: IClub = {
  id: 6782,
  razon: 'pish',
  direccion: 'whereas insecure manufacturing',
  telefono: 'curiously save',
  email: 'JoseEmilio.YbarraBatista83@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'avaricious woot',
};

export const sampleWithFullData: IClub = {
  id: 29992,
  razon: 'webbed',
  direccion: 'foolishly carefree',
  telefono: 'properly',
  email: 'Nicolas_AguileraSerrano@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'pace professional',
};

export const sampleWithNewData: NewClub = {
  razon: 'rudely',
  direccion: 'uh-huh towards',
  telefono: 'saviour',
  email: 'Isabel60@yahoo.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'babyish',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
