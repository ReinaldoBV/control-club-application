import dayjs from 'dayjs/esm';

import { IJugador, NewJugador } from './jugador.model';

export const sampleWithRequiredData: IJugador = {
  id: 157,
  idJugador: 15065,
  nroIdentificacion: 8386,
  tipoIdentificacion: 'RUT',
  nombres: 'muffled mod',
  apellidos: 'provided joyfully',
  nacionalidad: 'CHILENA',
  edad: 32606,
  fechaNacimiento: dayjs('2024-06-12'),
  numeroCamisa: 29739,
  contactoEmergencia: 'buttonhole save',
  calleAvenidaDireccion: 'nervously',
  numeroDireccion: 28055,
  numeroPersonal: 25616,
};

export const sampleWithPartialData: IJugador = {
  id: 23344,
  idJugador: 7401,
  nroIdentificacion: 31401,
  tipoIdentificacion: 'RUT',
  nombres: 'mummify detest',
  apellidos: 'sweetly',
  nacionalidad: 'DOMINICANA',
  edad: 30087,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 12358,
  contactoEmergencia: 'likewise for',
  calleAvenidaDireccion: 'absent unlucky',
  numeroDireccion: 31224,
  numeroPersonal: 10292,
  imagenJugador: '../fake-data/blob/hipster.png',
  imagenJugadorContentType: 'unknown',
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithFullData: IJugador = {
  id: 16975,
  idJugador: 5347,
  nroIdentificacion: 22356,
  tipoIdentificacion: 'RUT',
  nombres: 'coaxingly terribly desktop',
  apellidos: 'bashfully',
  nacionalidad: 'CHILENA',
  edad: 4782,
  fechaNacimiento: dayjs('2024-06-12'),
  numeroCamisa: 11238,
  contactoEmergencia: 'remorseful',
  calleAvenidaDireccion: 'abaft violently unlike',
  numeroDireccion: 13196,
  numeroPersonal: 30352,
  imagenJugador: '../fake-data/blob/hipster.png',
  imagenJugadorContentType: 'unknown',
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithNewData: NewJugador = {
  idJugador: 27156,
  nroIdentificacion: 17283,
  tipoIdentificacion: 'ESCOLAR',
  nombres: 'following defragment',
  apellidos: 'ha',
  nacionalidad: 'OTRA',
  edad: 4498,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 21873,
  contactoEmergencia: 'prise unlike phony',
  calleAvenidaDireccion: 'oof reassuringly',
  numeroDireccion: 27612,
  numeroPersonal: 26003,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
