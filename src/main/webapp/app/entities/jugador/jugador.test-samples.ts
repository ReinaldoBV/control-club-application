import dayjs from 'dayjs/esm';

import { IJugador, NewJugador } from './jugador.model';

export const sampleWithRequiredData: IJugador = {
  id: 17753,
  nroIdentificacion: 446,
  tipoIdentificacion: 'ESCOLAR',
  nombres: 'below frozen',
  apellidos: 'wipe broken yet',
  nacionalidad: 'OTRA',
  edad: 29739,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 23545,
  contactoEmergencia: 'although',
  calleAvenidaDireccion: 'although since',
  numeroDireccion: 5797,
  numeroPersonal: 9718,
};

export const sampleWithPartialData: IJugador = {
  id: 31401,
  nroIdentificacion: 659,
  tipoIdentificacion: 'PASAPORTE',
  nombres: 'even',
  apellidos: 'ugh psst',
  nacionalidad: 'OTRA',
  edad: 21162,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 13020,
  contactoEmergencia: 'ephemera',
  calleAvenidaDireccion: 'gee yawningly',
  numeroDireccion: 15900,
  numeroPersonal: 9450,
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithFullData: IJugador = {
  id: 22654,
  nroIdentificacion: 23301,
  tipoIdentificacion: 'ESCOLAR',
  nombres: 'the coaxingly terribly',
  apellidos: 'blindly',
  nacionalidad: 'OTRA',
  edad: 12588,
  fechaNacimiento: dayjs('2024-06-12'),
  numeroCamisa: 18323,
  contactoEmergencia: 'exactly',
  calleAvenidaDireccion: 'sweet abaft violently',
  numeroDireccion: 11624,
  numeroPersonal: 4170,
  imagenJugador: '../fake-data/blob/hipster.png',
  imagenJugadorContentType: 'unknown',
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithNewData: NewJugador = {
  nroIdentificacion: 16822,
  tipoIdentificacion: 'PASAPORTE',
  nombres: 'orange upward',
  apellidos: 'defragment folder',
  nacionalidad: 'COLOMBIANA',
  edad: 28840,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 2832,
  contactoEmergencia: 'unaccountably mod lest',
  calleAvenidaDireccion: 'than colorfully',
  numeroDireccion: 21408,
  numeroPersonal: 27612,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
