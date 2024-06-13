import dayjs from 'dayjs/esm';

import { IAnuncio, NewAnuncio } from './anuncio.model';

export const sampleWithRequiredData: IAnuncio = {
  id: 23578,
  titulo: 'unnecessarily',
  contenido: 'youthful slushy',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithPartialData: IAnuncio = {
  id: 30976,
  titulo: 'plus stucco hence',
  contenido: 'despite underneath',
  fechaPublicacion: dayjs('2024-06-12'),
};

export const sampleWithFullData: IAnuncio = {
  id: 6309,
  titulo: 'searchingly boo split',
  contenido: 'oust',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewAnuncio = {
  titulo: 'than rotating',
  contenido: 'notwithstanding fooey furiously',
  fechaPublicacion: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
