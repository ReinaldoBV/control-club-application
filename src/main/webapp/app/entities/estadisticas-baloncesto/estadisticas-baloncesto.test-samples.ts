import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from './estadisticas-baloncesto.model';

export const sampleWithRequiredData: IEstadisticasBaloncesto = {
  id: 19464,
};

export const sampleWithPartialData: IEstadisticasBaloncesto = {
  id: 14246,
  puntos: 20279,
  rebotes: 22056,
  bloqueos: 9685,
  porcentajeTiro: 6421.28,
};

export const sampleWithFullData: IEstadisticasBaloncesto = {
  id: 26031,
  puntos: 30849,
  rebotes: 8047,
  asistencias: 987,
  robos: 16945,
  bloqueos: 14102,
  porcentajeTiro: 17643.23,
};

export const sampleWithNewData: NewEstadisticasBaloncesto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
