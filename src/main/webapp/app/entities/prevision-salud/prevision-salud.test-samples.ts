import { IPrevisionSalud, NewPrevisionSalud } from './prevision-salud.model';

export const sampleWithRequiredData: IPrevisionSalud = {
  id: 6399,
  tipoPrevision: 'OTRO',
};

export const sampleWithPartialData: IPrevisionSalud = {
  id: 29485,
  tipoPrevision: 'OTRO',
};

export const sampleWithFullData: IPrevisionSalud = {
  id: 20430,
  tipoPrevision: 'NO_TIENE',
};

export const sampleWithNewData: NewPrevisionSalud = {
  tipoPrevision: 'OTRO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
