import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 23038,
  centroSalud: 'by um or',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 17072,
  centroSalud: 'against',
};

export const sampleWithFullData: ICentroSalud = {
  id: 32562,
  centroSalud: 'harmless since',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'sympathetically upset',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
