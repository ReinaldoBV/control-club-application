import dayjs from 'dayjs/esm';

import { IDirectivos, NewDirectivos } from './directivos.model';

export const sampleWithRequiredData: IDirectivos = {
  id: 29922,
  nombres: 'out',
  apellidos: 'near',
  cargo: 'miserably pilfer',
  telefono: 'mid limping amusing',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Laura_AnayaCano@hotmail.com',
};

export const sampleWithPartialData: IDirectivos = {
  id: 31445,
  nombres: 'off attorney',
  apellidos: 'thankfully ick',
  cargo: 'compassion pro unto',
  telefono: 'though iron ha',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Juana_BacaQuintana34@hotmail.com',
};

export const sampleWithFullData: IDirectivos = {
  id: 3199,
  nombres: 'unlace mountain unless',
  apellidos: 'decline whenever duh',
  cargo: 'aromatic quickly',
  telefono: 'via',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Juan.PenaNaranjo91@hotmail.com',
};

export const sampleWithNewData: NewDirectivos = {
  nombres: 'chilly',
  apellidos: 'corrupt absentmindedly',
  cargo: 'unto ew until',
  telefono: 'yet mmm',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Patricio.BurgosIglesias@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
