import { IMensaje, NewMensaje } from './mensaje.model';

export const sampleWithRequiredData: IMensaje = {
  id: 29077,
  remitenteId: 5819,
  destinatarioId: 27212,
  mensaje: 'resistance dimly cannulate',
};

export const sampleWithPartialData: IMensaje = {
  id: 18919,
  remitenteId: 25730,
  destinatarioId: 27093,
  mensaje: 'hence duh',
};

export const sampleWithFullData: IMensaje = {
  id: 4962,
  remitenteId: 31239,
  destinatarioId: 23201,
  mensaje: 'why tow-truck',
  leido: false,
};

export const sampleWithNewData: NewMensaje = {
  remitenteId: 12242,
  destinatarioId: 14585,
  mensaje: 'boo harmonious following',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
