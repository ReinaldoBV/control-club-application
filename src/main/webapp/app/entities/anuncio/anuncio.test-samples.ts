import dayjs from 'dayjs/esm';

import { IAnuncio, NewAnuncio } from './anuncio.model';

export const sampleWithRequiredData: IAnuncio = {
  id: 24152,
  titulo: 'whoa safely quixotic',
  contenido: 'upliftingly',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithPartialData: IAnuncio = {
  id: 2487,
  titulo: 'slimy across drat',
  contenido: 'gaseous',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithFullData: IAnuncio = {
  id: 21310,
  titulo: 'itch until',
  contenido: 'gigantic max or',
  fechaPublicacion: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewAnuncio = {
  titulo: 'er filthy neglected',
  contenido: 'stealthily gator incidentally',
  fechaPublicacion: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
