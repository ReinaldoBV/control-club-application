import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 14662,
  razon: 'gently ouch which',
  direccion: 'er',
  telefono: 'importance but',
  email: 'Rosa56@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'or',
};

export const sampleWithPartialData: IClub = {
  id: 18450,
  razon: 'gee certainly',
  direccion: 'launder',
  telefono: 'monumental',
  email: 'Ester_MunizZamora@yahoo.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'yum hike',
};

export const sampleWithFullData: IClub = {
  id: 18619,
  razon: 'supposing on shyly',
  direccion: 'yet carelessly smoothly',
  telefono: 'upon excepting',
  email: 'Guadalupe73@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'thoroughly however phew',
};

export const sampleWithNewData: NewClub = {
  razon: 'playfully kookily ack',
  direccion: 'throbbing athwart',
  telefono: 'isolate slowly',
  email: 'Homero56@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'reimburse inferior',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
