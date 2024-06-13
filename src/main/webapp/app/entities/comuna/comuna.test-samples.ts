import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 10655,
  comuna: 'zigzag lively',
};

export const sampleWithPartialData: IComuna = {
  id: 12553,
  comuna: 'squelch safely aw',
};

export const sampleWithFullData: IComuna = {
  id: 3891,
  comuna: 'lest age emerge',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'gifted stomach',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
