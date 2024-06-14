import { IPrevisionSalud, NewPrevisionSalud } from './prevision-salud.model';

export const sampleWithRequiredData: IPrevisionSalud = {
  id: 28683,
  tipoPrevision: 'NO_TIENE',
};

export const sampleWithPartialData: IPrevisionSalud = {
  id: 21777,
  tipoPrevision: 'OTRO',
};

export const sampleWithFullData: IPrevisionSalud = {
  id: 10741,
  tipoPrevision: 'NO_TIENE',
};

export const sampleWithNewData: NewPrevisionSalud = {
  tipoPrevision: 'NO_TIENE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
