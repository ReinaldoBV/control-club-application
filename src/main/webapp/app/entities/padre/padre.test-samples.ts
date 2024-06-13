import { IPadre, NewPadre } from './padre.model';

export const sampleWithRequiredData: IPadre = {
  id: 10745,
  nombres: 'immobilize that um',
  apellidos: 'ferociously plus',
  relacion: 'helpful handball',
  telefono: 'phew fool',
  asociado: false,
  email: 'Teresa22@gmail.com',
};

export const sampleWithPartialData: IPadre = {
  id: 1916,
  nombres: 'innocently engulf when',
  apellidos: 'ew far-flung into',
  relacion: 'drat so bravely',
  telefono: 'like even',
  asociado: true,
  email: 'Sergio85@yahoo.com',
};

export const sampleWithFullData: IPadre = {
  id: 23449,
  nombres: 'oh jail',
  apellidos: 'meanwhile drip',
  relacion: 'wrong',
  telefono: 'lottery',
  asociado: true,
  email: 'Cesar_UlibarriArteaga63@gmail.com',
};

export const sampleWithNewData: NewPadre = {
  nombres: 'opposite onto',
  apellidos: 'collapse without',
  relacion: 'so',
  telefono: 'however',
  asociado: true,
  email: 'Ignacio_PerezCarreon39@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
