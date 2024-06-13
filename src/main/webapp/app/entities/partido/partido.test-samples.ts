import dayjs from 'dayjs/esm';

import { IPartido, NewPartido } from './partido.model';

export const sampleWithRequiredData: IPartido = {
  id: 11559,
  fecha: dayjs('2024-06-13'),
  oponente: 'blah knavishly courageously',
};

export const sampleWithPartialData: IPartido = {
  id: 18843,
  fecha: dayjs('2024-06-13'),
  oponente: 'clump',
  resultado: 'wetly',
};

export const sampleWithFullData: IPartido = {
  id: 245,
  fecha: dayjs('2024-06-12'),
  oponente: 'bashfully demolish beneath',
  resultado: 'unnecessarily until',
};

export const sampleWithNewData: NewPartido = {
  fecha: dayjs('2024-06-13'),
  oponente: 'instead on collision',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
