import { IMensaje, NewMensaje } from './mensaje.model';

export const sampleWithRequiredData: IMensaje = {
  id: 10699,
  remitenteId: 32313,
  destinatarioId: 18428,
  mensaje: 'gadzooks',
};

export const sampleWithPartialData: IMensaje = {
  id: 3913,
  remitenteId: 22658,
  destinatarioId: 21788,
  mensaje: 'nature house pew',
  leido: false,
};

export const sampleWithFullData: IMensaje = {
  id: 10646,
  remitenteId: 27712,
  destinatarioId: 2985,
  mensaje: 'gah atop',
  leido: false,
};

export const sampleWithNewData: NewMensaje = {
  remitenteId: 29916,
  destinatarioId: 22714,
  mensaje: 'toward stash',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
