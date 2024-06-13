import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 12251,
  razon: 'violently flee',
  direccion: 'best anti',
  telefono: 'pillar without',
  email: 'Mariana.LoeraJaimes44@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'pessimistic',
};

export const sampleWithPartialData: IClub = {
  id: 558,
  razon: 'plus',
  direccion: 'wherever until than',
  telefono: 'clearly teeming',
  email: 'Laura0@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'yum zowie',
};

export const sampleWithFullData: IClub = {
  id: 25487,
  razon: 'hm best',
  direccion: 'majestic',
  telefono: 'frayed',
  email: 'Natalia.BaezConcepcion34@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'gloomy',
};

export const sampleWithNewData: NewClub = {
  razon: 'knavishly',
  direccion: 'furiously infinite',
  telefono: 'upon',
  email: 'AnaMaria77@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'brain mean',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
