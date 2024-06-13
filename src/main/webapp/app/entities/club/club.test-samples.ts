import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 22786,
  razon: 'juicy',
  direccion: 'ugh',
  telefono: 'questionably abnormally',
  email: 'Gloria.UrrutiaGaitan10@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'really that decrypt',
};

export const sampleWithPartialData: IClub = {
  id: 26860,
  razon: 'stain unlawful stealthily',
  direccion: 'duh',
  telefono: 'although wherever praise',
  email: 'Marilu.EnriquezMota@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'honestly always kangaroo',
};

export const sampleWithFullData: IClub = {
  id: 20039,
  razon: 'unnecessarily unto',
  direccion: 'tart anenst',
  telefono: 'beyond coinsurance',
  email: 'Patricio.AcevedoTorrez@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'yippee lined',
};

export const sampleWithNewData: NewClub = {
  razon: 'twitter',
  direccion: 'shaker recommence enjoy',
  telefono: 'kindly save',
  email: 'Esperanza_VillalpandoMedina22@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'tightly energetically',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
