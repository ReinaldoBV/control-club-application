import dayjs from 'dayjs/esm';

import { IEntrenamiento, NewEntrenamiento } from './entrenamiento.model';

export const sampleWithRequiredData: IEntrenamiento = {
  id: 25145,
  fechaHora: dayjs('2024-06-13'),
  duracion: 1442,
};

export const sampleWithPartialData: IEntrenamiento = {
  id: 29908,
  fechaHora: dayjs('2024-06-13'),
  duracion: 2518,
};

export const sampleWithFullData: IEntrenamiento = {
  id: 11626,
  fechaHora: dayjs('2024-06-13'),
  duracion: 28931,
};

export const sampleWithNewData: NewEntrenamiento = {
  fechaHora: dayjs('2024-06-13'),
  duracion: 11118,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
