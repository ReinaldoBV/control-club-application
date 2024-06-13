import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 25524,
  centroEducativo: 'energetically grown process',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 24542,
  centroEducativo: 'cleverly defend bleep',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 11144,
  centroEducativo: 'quickly um',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'yowza outside given',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
