import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 8281,
  nombres: 'painter',
  apellidos: 'following bronze',
  cargo: 'judgementally but',
  telefono: 'upon',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Raquel75@hotmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 18702,
  nombres: 'triumphantly jovial aboard',
  apellidos: 'atop boo whereas',
  cargo: 'before skinny',
  telefono: 'innocently ugh fancy',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'David42@gmail.com',
};

export const sampleWithFullData: IAsociados = {
  id: 23957,
  nombres: 'even temp',
  apellidos: 'well-documented civilian',
  cargo: 'mailing',
  telefono: 'exhaustion anti brr',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Natalia51@yahoo.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'painfully whoever',
  apellidos: 'restfully thin',
  cargo: 'glow',
  telefono: 'phew',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Rebeca.CorderoGuillen@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
