import { IBienes, NewBienes } from './bienes.model';

export const sampleWithRequiredData: IBienes = {
  id: 5,
  descripcion: 'without',
  cantidad: 27022,
  estado: 'yippee',
};

export const sampleWithPartialData: IBienes = {
  id: 16910,
  descripcion: 'provided as toward',
  cantidad: 9522,
  estado: 'including as versus',
};

export const sampleWithFullData: IBienes = {
  id: 21472,
  descripcion: 'phooey',
  cantidad: 17423,
  estado: 'interface',
};

export const sampleWithNewData: NewBienes = {
  descripcion: 'banquette ew',
  cantidad: 4323,
  estado: 'dumbfound pocket',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
