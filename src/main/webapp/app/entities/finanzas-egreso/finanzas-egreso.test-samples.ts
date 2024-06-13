import dayjs from 'dayjs/esm';

import { IFinanzasEgreso, NewFinanzasEgreso } from './finanzas-egreso.model';

export const sampleWithRequiredData: IFinanzasEgreso = {
  id: 21671,
  tipo: 'TRANSFERENCIA',
  descripcion: 'less phew',
  monto: 5690.61,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: IFinanzasEgreso = {
  id: 18798,
  tipo: 'CHEQUE',
  descripcion: 'meh seemingly appoint',
  monto: 12237.38,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: IFinanzasEgreso = {
  id: 1422,
  tipo: 'CHEQUE',
  descripcion: 'gee',
  monto: 13982.87,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewFinanzasEgreso = {
  tipo: 'EFECTIVO',
  descripcion: 'after woot extra-large',
  monto: 19696.46,
  fecha: dayjs('2024-06-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
