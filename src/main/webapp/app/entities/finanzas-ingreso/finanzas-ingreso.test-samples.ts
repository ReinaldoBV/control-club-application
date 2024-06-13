import dayjs from 'dayjs/esm';

import { IFinanzasIngreso, NewFinanzasIngreso } from './finanzas-ingreso.model';

export const sampleWithRequiredData: IFinanzasIngreso = {
  id: 112,
  tipo: 'CHEQUE',
  descripcion: 'colorfully gratefully amongst',
  monto: 5487.44,
  fecha: dayjs('2024-06-12'),
};

export const sampleWithPartialData: IFinanzasIngreso = {
  id: 22117,
  tipo: 'CHEQUE',
  descripcion: 'sick foolishly',
  monto: 22574.55,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: IFinanzasIngreso = {
  id: 18720,
  tipo: 'EFECTIVO',
  descripcion: 'fiercely',
  monto: 14959.65,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewFinanzasIngreso = {
  tipo: 'TRANSFERENCIA',
  descripcion: 'gah after vice',
  monto: 32576.89,
  fecha: dayjs('2024-06-12'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
