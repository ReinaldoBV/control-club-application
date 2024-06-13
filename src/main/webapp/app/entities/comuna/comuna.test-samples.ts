import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 13405,
  comuna: 'zowie uselessly',
};

export const sampleWithPartialData: IComuna = {
  id: 30758,
  comuna: 'last',
};

export const sampleWithFullData: IComuna = {
  id: 18644,
  comuna: 'um',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'cupola retaliate',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
