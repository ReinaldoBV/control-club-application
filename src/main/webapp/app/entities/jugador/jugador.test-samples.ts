import dayjs from 'dayjs/esm';

import { IJugador, NewJugador } from './jugador.model';

export const sampleWithRequiredData: IJugador = {
  id: 8386,
  nroIdentificacion: 5777,
  tipoIdentificacion: 'PASAPORTE',
  nombres: 'sardonic prevent',
  apellidos: 'worried dimly',
  nacionalidad: 'OTRA',
  edad: 29573,
  fechaNacimiento: dayjs('2024-06-12'),
  numeroCamisa: 21609,
  contactoEmergencia: 'while coexist nervously',
  calleAvenidaDireccion: 'yuck atop merrily',
  numeroDireccion: 7832,
  numeroPersonal: 7257,
};

export const sampleWithPartialData: IJugador = {
  id: 2169,
  nroIdentificacion: 56,
  tipoIdentificacion: 'RUT',
  nombres: 'caper ephemera',
  apellidos: 'gee yawningly',
  nacionalidad: 'DOMINICANA',
  edad: 9450,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 23301,
  contactoEmergencia: 'phooey belated',
  calleAvenidaDireccion: 'terribly',
  numeroDireccion: 481,
  numeroPersonal: 16747,
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithFullData: IJugador = {
  id: 21583,
  nroIdentificacion: 13748,
  tipoIdentificacion: 'RUT',
  nombres: 'behind',
  apellidos: 'whoever sweet',
  nacionalidad: 'VENEZOLANA',
  edad: 1311,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 21781,
  contactoEmergencia: 'differential that',
  calleAvenidaDireccion: 'prickly following defragment',
  numeroDireccion: 7190,
  numeroPersonal: 29255,
  imagenJugador: '../fake-data/blob/hipster.png',
  imagenJugadorContentType: 'unknown',
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithNewData: NewJugador = {
  nroIdentificacion: 24160,
  tipoIdentificacion: 'RUT',
  nombres: 'repeatedly unaccountably mod',
  apellidos: 'phony than colorfully',
  nacionalidad: 'COLOMBIANA',
  edad: 27612,
  fechaNacimiento: dayjs('2024-06-12'),
  numeroCamisa: 5757,
  contactoEmergencia: 'which collector scuttle',
  calleAvenidaDireccion: 'whose about carelessly',
  numeroDireccion: 27306,
  numeroPersonal: 6186,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
