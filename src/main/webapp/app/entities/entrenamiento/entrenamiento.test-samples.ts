import dayjs from 'dayjs/esm';

import { IEntrenamiento, NewEntrenamiento } from './entrenamiento.model';

export const sampleWithRequiredData: IEntrenamiento = {
  id: 25015,
  fechaHora: dayjs('2024-06-13'),
  duracion: 12143,
};

export const sampleWithPartialData: IEntrenamiento = {
  id: 31755,
  fechaHora: dayjs('2024-06-13'),
  duracion: 31213,
};

export const sampleWithFullData: IEntrenamiento = {
  id: 22852,
  fechaHora: dayjs('2024-06-13'),
  duracion: 16297,
};

export const sampleWithNewData: NewEntrenamiento = {
  fechaHora: dayjs('2024-06-13'),
  duracion: 400,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
