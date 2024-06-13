import dayjs from 'dayjs/esm';

import { ITorneosParticipaciones, NewTorneosParticipaciones } from './torneos-participaciones.model';

export const sampleWithRequiredData: ITorneosParticipaciones = {
  id: 6303,
  descripcion: 'association enfold',
  monto: 27216.58,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: ITorneosParticipaciones = {
  id: 10032,
  descripcion: 'fooey degrease if',
  monto: 18418.07,
  fecha: dayjs('2024-06-12'),
};

export const sampleWithFullData: ITorneosParticipaciones = {
  id: 18464,
  descripcion: 'pummel navigate junior',
  monto: 3414.16,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithNewData: NewTorneosParticipaciones = {
  descripcion: 'duh',
  monto: 7009.39,
  fecha: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
