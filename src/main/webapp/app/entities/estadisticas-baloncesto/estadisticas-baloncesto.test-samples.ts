import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from './estadisticas-baloncesto.model';

export const sampleWithRequiredData: IEstadisticasBaloncesto = {
  id: 10794,
};

export const sampleWithPartialData: IEstadisticasBaloncesto = {
  id: 19598,
  puntos: 29372,
  rebotes: 21092,
  bloqueos: 32073,
  porcentajeTiro: 10630.76,
};

export const sampleWithFullData: IEstadisticasBaloncesto = {
  id: 29585,
  puntos: 2202,
  rebotes: 5203,
  asistencias: 21051,
  robos: 15192,
  bloqueos: 2911,
  porcentajeTiro: 19000.68,
};

export const sampleWithNewData: NewEstadisticasBaloncesto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
