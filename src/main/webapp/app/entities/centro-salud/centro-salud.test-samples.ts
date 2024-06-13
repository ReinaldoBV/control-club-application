import { ICentroSalud, NewCentroSalud } from './centro-salud.model';

export const sampleWithRequiredData: ICentroSalud = {
  id: 4402,
  centroSalud: 'deserted',
};

export const sampleWithPartialData: ICentroSalud = {
  id: 3990,
  centroSalud: 'batter competent',
};

export const sampleWithFullData: ICentroSalud = {
  id: 16800,
  centroSalud: 'fancy',
};

export const sampleWithNewData: NewCentroSalud = {
  centroSalud: 'slosh to',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
