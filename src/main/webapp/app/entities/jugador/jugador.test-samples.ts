import dayjs from 'dayjs/esm';

import { IJugador, NewJugador } from './jugador.model';

export const sampleWithRequiredData: IJugador = {
  id: 446,
  idJugador: 18361,
  nroIdentificacion: 11256,
  tipoIdentificacion: 'RUT',
  nombres: 'digestion past',
  apellidos: 'damp',
  nacionalidad: 'VENEZOLANA',
  edad: 22001,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 32201,
  contactoEmergencia: 'yet',
  calleAvenidaDireccion: 'inasmuch um or',
  numeroDireccion: 28506,
  numeroPersonal: 6442,
};

export const sampleWithPartialData: IJugador = {
  id: 25616,
  idJugador: 5797,
  nroIdentificacion: 9718,
  tipoIdentificacion: 'ESCOLAR',
  nombres: 'whereas',
  apellidos: 'above',
  nacionalidad: 'DOMINICANA',
  edad: 7832,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 18319,
  contactoEmergencia: 'tourist',
  calleAvenidaDireccion: 'deceivingly mishandle',
  numeroDireccion: 2443,
  numeroPersonal: 15227,
};

export const sampleWithFullData: IJugador = {
  id: 19420,
  idJugador: 20300,
  nroIdentificacion: 18474,
  tipoIdentificacion: 'PASAPORTE',
  nombres: 'unless',
  apellidos: 'till transcribe amongst',
  nacionalidad: 'CHILENA',
  edad: 26476,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 16747,
  contactoEmergencia: 'ghostwrite clone',
  calleAvenidaDireccion: 'knotty gadzooks ick',
  numeroDireccion: 8500,
  numeroPersonal: 6446,
  imagenJugador: '../fake-data/blob/hipster.png',
  imagenJugadorContentType: 'unknown',
  documentoIdentificacion: '../fake-data/blob/hipster.png',
  documentoIdentificacionContentType: 'unknown',
};

export const sampleWithNewData: NewJugador = {
  idJugador: 30551,
  nroIdentificacion: 11624,
  tipoIdentificacion: 'RUT',
  nombres: 'sweaty upbeat',
  apellidos: 'youthfully platelet',
  nacionalidad: 'DOMINICANA',
  edad: 11654,
  fechaNacimiento: dayjs('2024-06-13'),
  numeroCamisa: 28840,
  contactoEmergencia: 'win',
  calleAvenidaDireccion: 'plonk',
  numeroDireccion: 17794,
  numeroPersonal: 29336,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
