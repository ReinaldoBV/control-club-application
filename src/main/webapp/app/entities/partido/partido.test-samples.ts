import dayjs from 'dayjs/esm';

import { IPartido, NewPartido } from './partido.model';

export const sampleWithRequiredData: IPartido = {
  id: 565,
  fecha: dayjs('2024-06-13'),
  oponente: 'out for',
};

export const sampleWithPartialData: IPartido = {
  id: 28539,
  fecha: dayjs('2024-06-13'),
  oponente: 'simplification cleverly bitterly',
  resultado: 'that glue consequently',
};

export const sampleWithFullData: IPartido = {
  id: 20142,
  fecha: dayjs('2024-06-13'),
  oponente: 'mozzarella aboard',
  resultado: 'brr subtle',
};

export const sampleWithNewData: NewPartido = {
  fecha: dayjs('2024-06-13'),
  oponente: 'and briskly sympathetically',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
