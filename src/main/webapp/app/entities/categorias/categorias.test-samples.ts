import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 9000,
  anioInicio: 2541,
  anioFinal: 20930,
  nombreCategoria: 'absentmindedly springboard putrid',
};

export const sampleWithPartialData: ICategorias = {
  id: 19581,
  anioInicio: 11232,
  anioFinal: 20295,
  nombreCategoria: 'helpless because',
};

export const sampleWithFullData: ICategorias = {
  id: 8253,
  anioInicio: 31309,
  anioFinal: 991,
  nombreCategoria: 'after',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 18627,
  anioFinal: 30064,
  nombreCategoria: 'circa',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
