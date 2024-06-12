import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 29606,
  login: '_htEF',
};

export const sampleWithPartialData: IUser = {
  id: 15865,
  login: '-',
};

export const sampleWithFullData: IUser = {
  id: 25298,
  login: 'e7tReI@4m\\\\ekpU',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
