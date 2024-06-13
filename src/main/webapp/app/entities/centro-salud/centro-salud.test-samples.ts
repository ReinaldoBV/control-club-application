import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 1325,
  centroSalud: 'whose julienne',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 11347,
  centroSalud: 'blah outsource message',
};

export const sampleWithFullData: ICentroSalud = {
  id: 18047,
  centroSalud: 'ick',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'as switch meanwhile',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
