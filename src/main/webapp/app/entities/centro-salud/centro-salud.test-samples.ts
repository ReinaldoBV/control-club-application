import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 30696,
  centroSalud: 'heavily',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 26308,
  centroSalud: 'blah amongst wise',
};

export const sampleWithFullData: ICentroSalud = {
  id: 6122,
  centroSalud: 'congressperson',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'circa',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
