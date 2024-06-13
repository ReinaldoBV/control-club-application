import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 15830,
  centroSalud: 'ick since',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 18710,
  centroSalud: 'awareness',
};

export const sampleWithFullData: ICentroSalud = {
  id: 862,
  centroSalud: 'dead',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'ha because',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
