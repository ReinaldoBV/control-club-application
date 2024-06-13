import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 29850,
  nombres: 'whoa odd faithfully',
  apellidos: 'plaintive wherever sequel',
  cargo: 'only except',
  telefono: 'supposing',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Ester62@hotmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 20874,
  nombres: 'afore',
  apellidos: 'dressing powerfully with',
  cargo: 'brr vitiate',
  telefono: 'plus',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Caridad_CortesGuajardo52@yahoo.com',
};

export const sampleWithFullData: IAsociados = {
  id: 5660,
  nombres: 'regal inside',
  apellidos: 'madly',
  cargo: 'supposing bag',
  telefono: 'continue enraged',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Pablo71@gmail.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'up',
  apellidos: 'glass',
  cargo: 'kiddingly imaginary',
  telefono: 'ugh',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Rafael.SaavedraSalas97@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
