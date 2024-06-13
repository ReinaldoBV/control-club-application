import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 4079,
  razon: 'failing',
  direccion: 'following gadzooks',
  telefono: 'ick',
  email: 'Mayte_HernandezArmenta46@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'inside',
};

export const sampleWithPartialData: IClub = {
  id: 5950,
  razon: 'yum',
  direccion: 'hm furiously',
  telefono: 'arid',
  email: 'Mateo.ChaconHolguin79@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'intelligent coo',
};

export const sampleWithFullData: IClub = {
  id: 31905,
  razon: 'whip hm trusty',
  direccion: 'aside duh',
  telefono: 'brand for',
  email: 'Ines61@gmail.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'troubleshoot coaxingly certainly',
};

export const sampleWithNewData: NewClub = {
  razon: 'monitor reluctantly',
  direccion: 'greatness incidentally',
  telefono: 'excitedly',
  email: 'Rosa27@gmail.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'chromakey willfully upon',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
