import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 28478,
  comuna: 'PROVIDENCIA',
};

export const sampleWithPartialData: IComuna = {
  id: 1728,
  comuna: 'PUDAHUEL',
};

export const sampleWithFullData: IComuna = {
  id: 17046,
  comuna: 'ESTACION_CENTRAL',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'RENCA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
