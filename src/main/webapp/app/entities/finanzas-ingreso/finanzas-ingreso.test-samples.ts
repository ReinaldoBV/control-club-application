import dayjs from 'dayjs/esm';

import { IFinanzasIngreso, NewFinanzasIngreso } from './finanzas-ingreso.model';

export const sampleWithRequiredData: IFinanzasIngreso = {
  id: 22918,
  tipo: 'EFECTIVO',
  descripcion: 'skipper shout',
  monto: 2618.71,
  fecha: dayjs('2024-06-12'),
};

export const sampleWithPartialData: IFinanzasIngreso = {
  id: 30149,
  tipo: 'TRANSFERENCIA',
  descripcion: 'always whereas',
  monto: 8602.98,
  fecha: dayjs('2024-06-12'),
};

export const sampleWithFullData: IFinanzasIngreso = {
  id: 14350,
  tipo: 'TRANSFERENCIA',
  descripcion: 'bitmap',
  monto: 17872.66,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewFinanzasIngreso = {
  tipo: 'TRANSFERENCIA',
  descripcion: 'off',
  monto: 1742.06,
  fecha: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
