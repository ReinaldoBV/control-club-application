import dayjs from 'dayjs/esm';

import { ITorneosParticipaciones, NewTorneosParticipaciones } from './torneos-participaciones.model';

export const sampleWithRequiredData: ITorneosParticipaciones = {
  id: 12717,
  descripcion: 'portend circa',
  monto: 12641.86,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: ITorneosParticipaciones = {
  id: 29373,
  descripcion: 'formalise when upon',
  monto: 8718.88,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: ITorneosParticipaciones = {
  id: 32236,
  descripcion: 'ah',
  monto: 10088.69,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewTorneosParticipaciones = {
  descripcion: 'eek canon',
  monto: 7609.02,
  fecha: dayjs('2024-06-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
