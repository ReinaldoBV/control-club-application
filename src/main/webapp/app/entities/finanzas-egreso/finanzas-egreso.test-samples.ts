import dayjs from 'dayjs/esm';

import { IFinanzasEgreso, NewFinanzasEgreso } from './finanzas-egreso.model';

export const sampleWithRequiredData: IFinanzasEgreso = {
  id: 8693,
  tipo: 'CHEQUE',
  descripcion: 'superficial front ordinary',
  monto: 4341.91,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: IFinanzasEgreso = {
  id: 13675,
  tipo: 'TRANSFERENCIA',
  descripcion: 'yawning',
  monto: 20297.86,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: IFinanzasEgreso = {
  id: 16565,
  tipo: 'EFECTIVO',
  descripcion: 'thrifty',
  monto: 15162.32,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewFinanzasEgreso = {
  tipo: 'CHEQUE',
  descripcion: 'fortune incompatible',
  monto: 21360.79,
  fecha: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
