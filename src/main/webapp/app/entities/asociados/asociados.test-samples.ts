import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 8048,
  nombres: 'but considering',
  apellidos: 'vastly',
  cargo: 'pest',
  telefono: 'flatboat spectacular',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'German95@gmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 9690,
  nombres: 'shimmering adjudicate',
  apellidos: 'past descriptive',
  cargo: 'apud lest',
  telefono: 'surprisingly yum wherever',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Mariana_UlloaOrnelas49@hotmail.com',
};

export const sampleWithFullData: IAsociados = {
  id: 10142,
  nombres: 'and dazzling',
  apellidos: 'afore competitor',
  cargo: 'poor',
  telefono: 'gadzooks',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Marta67@yahoo.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'phew',
  apellidos: 'reason tomorrow bleakly',
  cargo: 'unlike',
  telefono: 'furthermore equally huge',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Vicente34@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
