import { IComuna, NewComuna } from './comuna.model';

export const sampleWithRequiredData: IComuna = {
  id: 28478,
  comuna: 'properly reclaim',
};

export const sampleWithPartialData: IComuna = {
  id: 14271,
  comuna: 'anticodon',
};

export const sampleWithFullData: IComuna = {
  id: 27327,
  comuna: 'provided when',
};

export const sampleWithNewData: NewComuna = {
  comuna: 'commission',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
