import dayjs from 'dayjs/esm';

import { ICuentas, NewCuentas } from './cuentas.model';

export const sampleWithRequiredData: ICuentas = {
  id: 7842,
  tipo: 'PAGAR',
  descripcion: 'consequently usually',
  monto: 28935.91,
  nroCuotas: 8224,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PENDIENTE',
};

export const sampleWithPartialData: ICuentas = {
  id: 10272,
  tipo: 'COBRAR',
  descripcion: 'monsoon',
  monto: 24177.8,
  nroCuotas: 32703,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PAGADO',
};

export const sampleWithFullData: ICuentas = {
  id: 27809,
  tipo: 'PAGAR',
  descripcion: 'staff excluding boohoo',
  monto: 9418.72,
  nroCuotas: 8996,
  fechaVencimiento: dayjs('2024-06-12'),
  estado: 'COBRADO',
};

export const sampleWithNewData: NewCuentas = {
  tipo: 'COBRAR',
  descripcion: 'yuck',
  monto: 3871.44,
  nroCuotas: 26639,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PENDIENTE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
