import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 28478,
  comuna: 'PUDAHUEL',
};

export const sampleWithPartialData: IComuna = {
  id: 1728,
  comuna: 'PUENTE',
};

export const sampleWithFullData: IComuna = {
  id: 17046,
  comuna: 'ESTACION',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'RENCA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
