import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 30103,
  centroSalud: 'yahoo shyly',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 6208,
  centroSalud: 'ha',
};

export const sampleWithFullData: ICentroSalud = {
  id: 31437,
  centroSalud: 'woot indeed up',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'loudly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
