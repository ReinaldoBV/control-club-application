import { IBienes, NewBienes } from './bienes.model';

export const sampleWithRequiredData: IBienes = {
  id: 29794,
  descripcion: 'save professionalize',
  cantidad: 18894,
  estado: 'pfft scorch joint',
};

export const sampleWithPartialData: IBienes = {
  id: 22417,
  descripcion: 'combine young',
  cantidad: 21846,
  estado: 'furthermore tangible',
};

export const sampleWithFullData: IBienes = {
  id: 3232,
  descripcion: 'oh hearsay',
  cantidad: 4135,
  estado: 'why till worldly',
};

export const sampleWithNewData: NewBienes = {
  descripcion: 'enormous provided farming',
  cantidad: 21186,
  estado: 'furthermore',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
