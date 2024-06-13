import { ICategorias, NewCategorias } from './categorias.model';

export const sampleWithRequiredData: ICategorias = {
  id: 32414,
  anioInicio: 12721,
  anioFinal: 19131,
  nombreCategoria: 'unless checkroom',
};

export const sampleWithPartialData: ICategorias = {
  id: 26680,
  anioInicio: 28105,
  anioFinal: 11048,
  nombreCategoria: 'assign when',
};

export const sampleWithFullData: ICategorias = {
  id: 31925,
  anioInicio: 15669,
  anioFinal: 10527,
  nombreCategoria: 'ginseng failing',
};

export const sampleWithNewData: NewCategorias = {
  anioInicio: 9586,
  anioFinal: 3683,
  nombreCategoria: 'sometimes before stark',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
