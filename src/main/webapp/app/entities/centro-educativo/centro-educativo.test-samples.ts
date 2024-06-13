import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 10556,
  centroEducativo: 'hence immure or',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 8750,
  centroEducativo: 'unabashedly',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 9469,
  centroEducativo: 'what blissfully fluid',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'zowie',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
