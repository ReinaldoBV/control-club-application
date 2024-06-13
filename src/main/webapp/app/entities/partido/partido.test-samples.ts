import dayjs from 'dayjs/esm';

import { IPartido, NewPartido } from './partido.model';

export const sampleWithRequiredData: IPartido = {
  id: 26152,
  fecha: dayjs('2024-06-13'),
  oponente: 'following ack versus',
};

export const sampleWithPartialData: IPartido = {
  id: 30273,
  fecha: dayjs('2024-06-13'),
  oponente: 'hear',
  resultado: 'helpfully',
};

export const sampleWithFullData: IPartido = {
  id: 15656,
  fecha: dayjs('2024-06-13'),
  oponente: 'how outside outrageous',
  resultado: 'whoever',
};

export const sampleWithNewData: NewPartido = {
  fecha: dayjs('2024-06-13'),
  oponente: 'epee shell geez',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
