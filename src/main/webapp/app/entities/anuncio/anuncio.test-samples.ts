import dayjs from 'dayjs/esm';

import { IAnuncio, NewAnuncio } from './anuncio.model';

export const sampleWithRequiredData: IAnuncio = {
  id: 9548,
  titulo: 'deeply oof',
  contenido: 'violently ouch severe',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithPartialData: IAnuncio = {
  id: 32021,
  titulo: 'youthfully',
  contenido: 'hmph survey wretched',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithFullData: IAnuncio = {
  id: 10781,
  titulo: 'boohoo',
  contenido: 'hmph opossum corps',
  fechaPublicacion: dayjs('2024-06-12'),
};

export const sampleWithNewData: NewAnuncio = {
  titulo: 'drench waterski',
  contenido: 'till',
  fechaPublicacion: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
