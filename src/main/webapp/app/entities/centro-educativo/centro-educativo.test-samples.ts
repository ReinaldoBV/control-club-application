import { ICentroEducativo, NewCentroEducativo } from './centro-educativo.model';

export const sampleWithRequiredData: ICentroEducativo = {
  id: 20969,
  centroEducativo: 'by patent wrongly',
};

export const sampleWithPartialData: ICentroEducativo = {
  id: 14229,
  centroEducativo: 'notwithstanding anti',
};

export const sampleWithFullData: ICentroEducativo = {
  id: 28004,
  centroEducativo: 'mainstream',
};

export const sampleWithNewData: NewCentroEducativo = {
  centroEducativo: 'safely squish before',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
