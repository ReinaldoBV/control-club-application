import { IPadre, NewPadre } from './padre.model';

export const sampleWithRequiredData: IPadre = {
  id: 14098,
  nombres: 'pain',
  apellidos: 'puzzling',
  relacion: 'swoosh',
  telefono: 'irresponsible geez grocery',
  asociado: false,
  email: 'Marisol_QuinteroOrozco@gmail.com',
};

export const sampleWithPartialData: IPadre = {
  id: 19137,
  nombres: 'regenerate so ew',
  apellidos: 'constant doubtfully',
  relacion: 'fedora past',
  telefono: 'request',
  asociado: false,
  email: 'Gregorio86@gmail.com',
};

export const sampleWithFullData: IPadre = {
  id: 2022,
  nombres: 'quick-witted colossal outside',
  apellidos: 'often hollow',
  relacion: 'below',
  telefono: 'above',
  asociado: false,
  email: 'Benjamin87@gmail.com',
};

export const sampleWithNewData: NewPadre = {
  nombres: 'if beneath',
  apellidos: 'and oof',
  relacion: 'burden',
  telefono: 'terrible since',
  asociado: false,
  email: 'Joaquin87@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
