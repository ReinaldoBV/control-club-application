import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 27484,
  razon: 'meantime yuck woot',
  direccion: 'athwart',
  telefono: 'standing alongside good-natured',
  email: 'Blanca37@hotmail.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'hence',
};

export const sampleWithPartialData: IClub = {
  id: 4022,
  razon: 'cleft psst',
  direccion: 'sweetly alongside hungrily',
  telefono: 'hideous',
  email: 'Victor.CarreonMota88@yahoo.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'behind during before',
};

export const sampleWithFullData: IClub = {
  id: 13062,
  razon: 'anenst out',
  direccion: 'pint shrilly',
  telefono: 'uh-huh knowingly anguished',
  email: 'Benjamin_GodinezUrias@gmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'whoever',
};

export const sampleWithNewData: NewClub = {
  razon: 'selfishly mouse unscramble',
  direccion: 'birdcage',
  telefono: 'oddball',
  email: 'Gerardo7@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'knavishly dear after',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
