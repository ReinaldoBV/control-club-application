export interface ICategorias {
  id: number;
  anioInicio?: number | null;
  anioFinal?: number | null;
  nombreCategoria?: string | null;
}

export type NewCategorias = Omit<ICategorias, 'id'> & { id: null };
