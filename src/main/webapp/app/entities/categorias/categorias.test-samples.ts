import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 18015,
  anioInicio: 16380,
  anioFinal: 15005,
  nombreCategoria: 'what after',
};

export const sampleWithPartialData: ICategorias = {
  id: 4321,
  anioInicio: 11941,
  anioFinal: 9457,
  nombreCategoria: 'hm',
};

export const sampleWithFullData: ICategorias = {
  id: 28364,
  anioInicio: 18341,
  anioFinal: 8284,
  nombreCategoria: 'to numb enforce',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 13756,
  anioFinal: 12884,
  nombreCategoria: 'whoever elegantly afore',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
