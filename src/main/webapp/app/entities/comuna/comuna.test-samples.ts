import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 20984,
  comuna: 'seldom',
};

export const sampleWithPartialData: IComuna = {
  id: 25190,
  comuna: 'noisily some sans',
};

export const sampleWithFullData: IComuna = {
  id: 14557,
  comuna: 'fickle ruby',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'gah till madly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
