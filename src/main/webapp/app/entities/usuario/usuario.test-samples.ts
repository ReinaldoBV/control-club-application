import { IUsuario, NewUsuario } from './usuario.model';

export const sampleWithRequiredData: IUsuario = {
  id: 6113,
  username: 'furthermore',
  password: 'grandiose aside',
  rol: 'JUGADOR',
};

export const sampleWithPartialData: IUsuario = {
  id: 24058,
  username: 'including spectacular unselfish',
  password: 'yippee inasmuch',
  rol: 'ADMINISTRADOR',
};

export const sampleWithFullData: IUsuario = {
  id: 7646,
  username: 'mule drug near',
  password: 'pathology past',
  rol: 'JUGADOR',
};

export const sampleWithNewData: NewUsuario = {
  username: 'above lean until',
  password: 'punch creamy',
  rol: 'JUGADOR',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
