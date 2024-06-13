import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 27132,
  centroEducativo: 'very',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 3381,
  centroEducativo: 'kookily depth stranger',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 585,
  centroEducativo: 'until provided',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'whoa softly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
