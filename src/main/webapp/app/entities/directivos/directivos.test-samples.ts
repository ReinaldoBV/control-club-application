import dayjs from 'dayjs/esm';

import { IDirectivos, NewDirectivos } from './directivos.model';

export const sampleWithRequiredData: IDirectivos = {
  id: 6635,
  nombres: 'girl remains',
  apellidos: 'sympathetically pish',
  cargo: 'in like abaft',
  telefono: 'bumper',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Natalia_CardenasCarrasco0@yahoo.com',
};

export const sampleWithPartialData: IDirectivos = {
  id: 27720,
  nombres: 'so stormy',
  apellidos: 'noisily who geez',
  cargo: 'true',
  telefono: 'out frilly',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Rebeca_CalderonMolina95@hotmail.com',
};

export const sampleWithFullData: IDirectivos = {
  id: 23024,
  nombres: 'before frenetically extremely',
  apellidos: 'clever vulture once',
  cargo: 'who which',
  telefono: 'promulgate',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Gonzalo_PerezSosa@hotmail.com',
};

export const sampleWithNewData: NewDirectivos = {
  nombres: 'knotty toward sans',
  apellidos: 'roasted',
  cargo: 'winged whenever geez',
  telefono: 'veldt',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Rosario95@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
