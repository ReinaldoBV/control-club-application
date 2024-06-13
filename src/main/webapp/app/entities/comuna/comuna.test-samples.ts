import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 30808,
  comuna: 'warmly',
};

export const sampleWithPartialData: IComuna = {
  id: 15612,
  comuna: 'gather relieved webbed',
};

export const sampleWithFullData: IComuna = {
  id: 25406,
  comuna: 'loftily prioritise',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'yet yuck',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
