import dayjs from 'dayjs/esm';

import { ICuentas, NewCuentas } from './cuentas.model';

export const sampleWithRequiredData: ICuentas = {
  id: 19463,
  tipo: 'PAGAR',
  descripcion: 'sock carefully lionise',
  monto: 25682.51,
  nroCuotas: 53,
  fechaVencimiento: dayjs('2024-06-12'),
  estado: 'COBRADO',
};

export const sampleWithPartialData: ICuentas = {
  id: 24050,
  tipo: 'COBRAR',
  descripcion: 'once',
  monto: 31182.23,
  nroCuotas: 24950,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'PAGADO',
};

export const sampleWithFullData: ICuentas = {
  id: 23284,
  tipo: 'PAGAR',
  descripcion: 'save expectorate',
  monto: 31877.67,
  nroCuotas: 23147,
  fechaVencimiento: dayjs('2024-06-12'),
  estado: 'PAGADO',
};

export const sampleWithNewData: NewCuentas = {
  tipo: 'PAGAR',
  descripcion: 'weird',
  monto: 6142.74,
  nroCuotas: 205,
  fechaVencimiento: dayjs('2024-06-13'),
  estado: 'COBRADO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
