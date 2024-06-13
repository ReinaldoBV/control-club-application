import { IBienes, NewBienes } from './bienes.model';

export const sampleWithRequiredData: IBienes = {
  id: 27847,
  descripcion: 'stadium without',
  cantidad: 26514,
  estado: 'while',
};

export const sampleWithPartialData: IBienes = {
  id: 6257,
  descripcion: 'decent oddly phew',
  cantidad: 1937,
  estado: 'in',
};

export const sampleWithFullData: IBienes = {
  id: 32209,
  descripcion: 'yet',
  cantidad: 21262,
  estado: 'suppose modulo',
};

export const sampleWithNewData: NewBienes = {
  descripcion: 'firsthand',
  cantidad: 7089,
  estado: 'form ick',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
