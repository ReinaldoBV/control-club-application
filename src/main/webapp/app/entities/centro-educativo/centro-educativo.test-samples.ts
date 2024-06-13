import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 8610,
  centroEducativo: 'softly',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 27566,
  centroEducativo: 'mission',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 7354,
  centroEducativo: 'before times rush',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'confusion',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
