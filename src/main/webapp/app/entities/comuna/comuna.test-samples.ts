import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 4733,
  comuna: 'roger',
};

export const sampleWithPartialData: IComuna = {
  id: 15494,
  comuna: 'tremendously because yuck',
};

export const sampleWithFullData: IComuna = {
  id: 23483,
  comuna: 'furthermore wait',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'nor analyse',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
