import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: '8032293f-d633-4715-8ab1-ccb7a48b8256',
};

export const sampleWithPartialData: IAuthority = {
  name: 'be7766a6-b171-4e68-8d33-1d63b2325a6f',
};

export const sampleWithFullData: IAuthority = {
  name: 'ccbc6f27-0101-45ed-b3eb-9c77eb0b2f17',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
