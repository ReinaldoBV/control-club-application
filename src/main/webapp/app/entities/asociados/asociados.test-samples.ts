import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 15112,
  nombres: 'unto worse',
  apellidos: 'suffocation gracious qua',
  cargo: 'gutter whoa',
  telefono: 'outrun plagiarism that',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Elena.PadronNavarro88@gmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 14048,
  nombres: 'instead for',
  apellidos: 'grateful if oh',
  cargo: 'in or',
  telefono: 'unto',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Adan_MelendezNoriega@yahoo.com',
};

export const sampleWithFullData: IAsociados = {
  id: 6226,
  nombres: 'once supposing',
  apellidos: 'needily continue enraged',
  cargo: 'breadfruit forenenst',
  telefono: 'fowl abolish jovial',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Ruben.SantacruzAranda98@hotmail.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'whereas',
  apellidos: 'mmm',
  cargo: 'rotating',
  telefono: 'forget achiever',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Pedro46@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
