import dayjs from 'dayjs/esm';

import { ICuentas, NewCuentas } from './cuentas.model';

export const sampleWithRequiredData: ICuentas = {
  id: 22966,
  tipo: 'COBRAR',
  descripcion: 'whoa',
  monto: 8951.63,
  nroCuotas: 31741,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'COBRADO',
};

export const sampleWithPartialData: ICuentas = {
  id: 21126,
  tipo: 'COBRAR',
  descripcion: 'aside',
  monto: 10272.01,
  nroCuotas: 28518,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PENDIENTE',
};

export const sampleWithFullData: ICuentas = {
  id: 2091,
  tipo: 'PAGAR',
  descripcion: 'next',
  monto: 27808.66,
  nroCuotas: 2369,
  fechaVencimiento: dayjs('2024-06-12'),
  estado: 'PENDIENTE',
};

export const sampleWithNewData: NewCuentas = {
  tipo: 'COBRAR',
  descripcion: 'between',
  monto: 28589.41,
  nroCuotas: 7774,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PAGADO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
