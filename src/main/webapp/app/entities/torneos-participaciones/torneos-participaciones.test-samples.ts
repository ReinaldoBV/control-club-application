import dayjs from 'dayjs/esm';

import { ITorneosParticipaciones, NewTorneosParticipaciones } from './torneos-participaciones.model';

export const sampleWithRequiredData: ITorneosParticipaciones = {
  id: 14524,
  descripcion: 'forsake subedit terraform',
  monto: 13386.18,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: ITorneosParticipaciones = {
  id: 26738,
  descripcion: 'boo brr',
  monto: 14231.23,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: ITorneosParticipaciones = {
  id: 7947,
  descripcion: 'relay',
  monto: 28881.88,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewTorneosParticipaciones = {
  descripcion: 'neighboring',
  monto: 17684.4,
  fecha: dayjs('2024-06-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
