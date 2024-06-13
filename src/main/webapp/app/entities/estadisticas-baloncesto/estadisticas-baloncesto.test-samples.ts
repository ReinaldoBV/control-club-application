import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from './estadisticas-baloncesto.model';

export const sampleWithRequiredData: IEstadisticasBaloncesto = {
  id: 21176,
};

export const sampleWithPartialData: IEstadisticasBaloncesto = {
  id: 26019,
  robos: 6917,
  porcentajeTiro: 14043.01,
};

export const sampleWithFullData: IEstadisticasBaloncesto = {
  id: 32083,
  puntos: 3039,
  rebotes: 20920,
  asistencias: 9453,
  robos: 10928,
  bloqueos: 1901,
  porcentajeTiro: 14029.03,
};

export const sampleWithNewData: NewEstadisticasBaloncesto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
