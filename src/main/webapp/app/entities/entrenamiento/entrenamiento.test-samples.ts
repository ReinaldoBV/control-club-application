import dayjs from 'dayjs/esm';

import { IEntrenamiento, NewEntrenamiento } from './entrenamiento.model';

export const sampleWithRequiredData: IEntrenamiento = {
  id: 437,
  fechaHora: dayjs('2024-06-13'),
  duracion: 2047,
};

export const sampleWithPartialData: IEntrenamiento = {
  id: 23899,
  fechaHora: dayjs('2024-06-12'),
  duracion: 10249,
};

export const sampleWithFullData: IEntrenamiento = {
  id: 20628,
  fechaHora: dayjs('2024-06-13'),
  duracion: 2873,
};

export const sampleWithNewData: NewEntrenamiento = {
  fechaHora: dayjs('2024-06-12'),
  duracion: 25889,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
