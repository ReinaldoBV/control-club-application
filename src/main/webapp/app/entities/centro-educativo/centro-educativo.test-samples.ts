import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 4845,
  centroEducativo: 'tonight',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 10022,
  centroEducativo: 'bah',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 32233,
  centroEducativo: 'opposite',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'edify along unburden',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
