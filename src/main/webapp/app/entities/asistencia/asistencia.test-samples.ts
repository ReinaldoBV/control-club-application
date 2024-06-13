import dayjs from 'dayjs/esm';

import { IAsistencia, NewAsistencia } from './asistencia.model';

export const sampleWithRequiredData: IAsistencia = {
  id: 32194,
  tipo: 'ENTRENAMIENTO',
  idEvento: 28466,
  fecha: dayjs('2024-06-13'),
  asistencia: false,
};

export const sampleWithPartialData: IAsistencia = {
  id: 15312,
  tipo: 'PARTIDO',
  idEvento: 2020,
  fecha: dayjs('2024-06-13'),
  asistencia: false,
};

export const sampleWithFullData: IAsistencia = {
  id: 11802,
  tipo: 'ENTRENAMIENTO',
  idEvento: 10123,
  fecha: dayjs('2024-06-13'),
  asistencia: true,
};

export const sampleWithNewData: NewAsistencia = {
  tipo: 'PARTIDO',
  idEvento: 68,
  fecha: dayjs('2024-06-13'),
  asistencia: true,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
