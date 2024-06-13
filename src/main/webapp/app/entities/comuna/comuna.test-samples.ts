import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 10441,
  comuna: 'uh-huh chapter',
};

export const sampleWithPartialData: IComuna = {
  id: 18404,
  comuna: 'intent',
};

export const sampleWithFullData: IComuna = {
  id: 11323,
  comuna: 'cruelly',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'volunteer',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
