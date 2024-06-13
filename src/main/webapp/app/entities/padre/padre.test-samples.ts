import { IPadre, NewPadre } from './padre.model';

export const sampleWithRequiredData: IPadre = {
  id: 8118,
  nombres: 'necessary following now',
  apellidos: 'lament psst rectangular',
  relacion: 'yap',
  telefono: 'degenerate trump',
  asociado: false,
  email: 'Alicia6@yahoo.com',
};

export const sampleWithPartialData: IPadre = {
  id: 12106,
  nombres: 'geez standard hopelessly',
  apellidos: 'because tag successfully',
  relacion: 'now yum selfish',
  telefono: 'once till',
  asociado: false,
  email: 'Debora.NevarezValencia16@yahoo.com',
};

export const sampleWithFullData: IPadre = {
  id: 30478,
  nombres: 'upside-down although',
  apellidos: 'television afore tensor',
  relacion: 'diagram',
  telefono: 'underneath suddenly',
  asociado: true,
  email: 'Teodoro_PedrazaJasso4@yahoo.com',
};

export const sampleWithNewData: NewPadre = {
  nombres: 'following',
  apellidos: 'well',
  relacion: 'contribution immediately oh',
  telefono: 'rehearse comeback',
  asociado: false,
  email: 'Alicia.ValleGuardado28@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
