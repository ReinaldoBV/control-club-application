import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from './estadisticas-baloncesto.model';

export const sampleWithRequiredData: IEstadisticasBaloncesto = {
  id: 16846,
};

export const sampleWithPartialData: IEstadisticasBaloncesto = {
  id: 17764,
  rebotes: 14,
  asistencias: 16503,
  robos: 6428,
  porcentajeTiro: 25546.19,
};

export const sampleWithFullData: IEstadisticasBaloncesto = {
  id: 26971,
  puntos: 3853,
  rebotes: 16975,
  asistencias: 22121,
  robos: 5458,
  bloqueos: 18822,
  porcentajeTiro: 25807.18,
};

export const sampleWithNewData: NewEstadisticasBaloncesto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
