import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 5449,
  centroSalud: 'nifty crepe inasmuch',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 5304,
  centroSalud: 'ew indication',
};

export const sampleWithFullData: ICentroSalud = {
  id: 1067,
  centroSalud: 'shakily nor',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'softly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
