import dayjs from 'dayjs/esm';

import { IPartido, NewPartido } from './partido.model';

export const sampleWithRequiredData: IPartido = {
  id: 24273,
  fecha: dayjs('2024-06-13'),
  oponente: 'weakly aha',
};

export const sampleWithPartialData: IPartido = {
  id: 1599,
  fecha: dayjs('2024-06-13'),
  oponente: 'criticize yum scary',
  resultado: 'prisoner otter whenever',
};

export const sampleWithFullData: IPartido = {
  id: 28231,
  fecha: dayjs('2024-06-13'),
  oponente: 'slop deselect promptly',
  resultado: 'across',
};

export const sampleWithNewData: NewPartido = {
  fecha: dayjs('2024-06-13'),
  oponente: 'among',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
