import { IPadre, NewPadre } from './padre.model';

export const sampleWithRequiredData: IPadre = {
  id: 11646,
  nombres: 'gabble considering now',
  apellidos: 'interfere er',
  relacion: 'portly',
  telefono: 'jubilantly browbeat',
  asociado: true,
  email: 'MariaCristina_ZavalaMacias89@hotmail.com',
};

export const sampleWithPartialData: IPadre = {
  id: 18841,
  nombres: 'lavish',
  apellidos: 'fooey',
  relacion: 'gadzooks',
  telefono: 'election pfft given',
  asociado: true,
  email: 'Cristina67@hotmail.com',
};

export const sampleWithFullData: IPadre = {
  id: 16006,
  nombres: 'eek',
  apellidos: 'underlie portion crude',
  relacion: 'ill-informed transport yahoo',
  telefono: 'athletic up',
  asociado: false,
  email: 'Rebeca_SotoArredondo@hotmail.com',
};

export const sampleWithNewData: NewPadre = {
  nombres: 'thunderous stalk',
  apellidos: 'flawed',
  relacion: 'besides',
  telefono: 'plant haunting across',
  asociado: true,
  email: 'Victor64@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
