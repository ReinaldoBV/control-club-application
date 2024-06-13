import { IMensaje, NewMensaje } from './mensaje.model';

export const sampleWithRequiredData: IMensaje = {
  id: 575,
  remitenteId: 14667,
  destinatarioId: 31341,
  mensaje: 'on',
};

export const sampleWithPartialData: IMensaje = {
  id: 9406,
  remitenteId: 8089,
  destinatarioId: 8941,
  mensaje: 'jacket electrocute even',
};

export const sampleWithFullData: IMensaje = {
  id: 27031,
  remitenteId: 25840,
  destinatarioId: 1270,
  mensaje: 'underneath string',
  leido: false,
};

export const sampleWithNewData: NewMensaje = {
  remitenteId: 19660,
  destinatarioId: 12643,
  mensaje: 'drat',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
