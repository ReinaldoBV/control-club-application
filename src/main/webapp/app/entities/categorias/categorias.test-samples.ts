import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 27097,
  anioInicio: 8199,
  anioFinal: 3889,
  nombreCategoria: 'declare dimly',
};

export const sampleWithPartialData: ICategorias = {
  id: 3662,
  anioInicio: 26592,
  anioFinal: 11901,
  nombreCategoria: 'even partially',
};

export const sampleWithFullData: ICategorias = {
  id: 18280,
  anioInicio: 29291,
  anioFinal: 4175,
  nombreCategoria: 'like geez',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 9201,
  anioFinal: 14381,
  nombreCategoria: 'knobby unexpectedly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
