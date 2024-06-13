import { IPrevisionSalud, NewPrevisionSalud } from './prevision-salud.model';

export const sampleWithRequiredData: IPrevisionSalud = {
  id: 30577,
  tipoPrevision: 'FONASA',
};

export const sampleWithPartialData: IPrevisionSalud = {
  id: 25631,
  tipoPrevision: 'OTRO',
};

export const sampleWithFullData: IPrevisionSalud = {
  id: 15251,
  tipoPrevision: 'OTRO',
};

export const sampleWithNewData: NewPrevisionSalud = {
  tipoPrevision: 'FONASA',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
