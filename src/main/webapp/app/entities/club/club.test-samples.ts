import dayjs from 'dayjs/esm';

import { IClub, NewClub } from './club.model';

export const sampleWithRequiredData: IClub = {
  id: 1806,
  razon: 'despite package modern',
  direccion: 'or',
  telefono: 'solemnly unlike yowza',
  email: 'Daniela53@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'ick upliftingly fob',
};

export const sampleWithPartialData: IClub = {
  id: 24477,
  razon: 'overdue notable',
  direccion: 'coarse',
  telefono: 'yippee raffle',
  email: 'Gonzalo_OroscoOrellana@yahoo.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'amid',
};

export const sampleWithFullData: IClub = {
  id: 10200,
  razon: 'for against woodchuck',
  direccion: 'spot queen',
  telefono: 'steady knowingly instead',
  email: 'Cecilia_LimonRoybal31@hotmail.com',
  fechaFundacion: dayjs('2024-06-13'),
  presidente: 'bah',
};

export const sampleWithNewData: NewClub = {
  razon: 'leg yowza',
  direccion: 'whoa against positive',
  telefono: 'and gosh',
  email: 'David35@hotmail.com',
  fechaFundacion: dayjs('2024-06-12'),
  presidente: 'whenever',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
