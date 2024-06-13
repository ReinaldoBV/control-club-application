import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 17147,
  anioInicio: 26703,
  anioFinal: 18060,
  nombreCategoria: 'abaft',
};

export const sampleWithPartialData: ICategorias = {
  id: 2712,
  anioInicio: 15324,
  anioFinal: 24926,
  nombreCategoria: 'probable',
};

export const sampleWithFullData: ICategorias = {
  id: 18770,
  anioInicio: 2535,
  anioFinal: 29378,
  nombreCategoria: 'trick medal',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 12245,
  anioFinal: 399,
  nombreCategoria: 'buttery',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
