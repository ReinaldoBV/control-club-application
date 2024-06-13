import dayjs from 'dayjs/esm';

import { ITorneosParticipaciones, NewTorneosParticipaciones } from './torneos-participaciones.model';

export const sampleWithRequiredData: ITorneosParticipaciones = {
  id: 25294,
  descripcion: 'fight',
  monto: 3435.94,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithPartialData: ITorneosParticipaciones = {
  id: 31590,
  descripcion: 'why enormous yet',
  monto: 27020.24,
  fecha: dayjs('2024-06-13'),
};

export const sampleWithFullData: ITorneosParticipaciones = {
  id: 31890,
  descripcion: 'glug blissfully throughout',
  monto: 5320.39,
  fecha: dayjs('2024-06-12'),
};

export const sampleWithNewData: NewTorneosParticipaciones = {
  descripcion: 'who',
  monto: 710.3,
  fecha: dayjs('2024-06-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
