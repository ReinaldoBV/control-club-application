import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 13602,
  anioInicio: 14651,
  anioFinal: 27504,
  nombreCategoria: 'although',
};

export const sampleWithPartialData: ICategorias = {
  id: 24439,
  anioInicio: 28795,
  anioFinal: 29410,
  nombreCategoria: 'beyond aha whether',
};

export const sampleWithFullData: ICategorias = {
  id: 1686,
  anioInicio: 12244,
  anioFinal: 28677,
  nombreCategoria: 'ack busily after',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 6689,
  anioFinal: 21931,
  nombreCategoria: 'impractical authorisation wrongly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
