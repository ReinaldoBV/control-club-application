import dayjs from 'dayjs/esm';

import { IDirectivos, NewDirectivos } from './directivos.model';

export const sampleWithRequiredData: IDirectivos = {
  id: 21328,
  nombres: 'hm brr',
  apellidos: 'briefly',
  cargo: 'boohoo likewise but',
  telefono: 'strictly during whether',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'MariadelCarmen68@hotmail.com',
};

export const sampleWithPartialData: IDirectivos = {
  id: 20258,
  nombres: 'until around trill',
  apellidos: 'um vast although',
  cargo: 'bow',
  telefono: 'assistance',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Rosalia_ChapaMendoza87@gmail.com',
};

export const sampleWithFullData: IDirectivos = {
  id: 2279,
  nombres: 'unless gosh',
  apellidos: 'phew peek glower',
  cargo: 'round unto',
  telefono: 'lender',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Ariadna13@hotmail.com',
};

export const sampleWithNewData: NewDirectivos = {
  nombres: 'oh',
  apellidos: 'eek remark more',
  cargo: 'hmph though',
  telefono: 'whose staid',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Gerardo24@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
