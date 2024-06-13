import { RolUsuario } from 'app/entities/enumerations/rol-usuario.model';

export interface IUsuario {
  id: number;
  username?: string | null;
  password?: string | null;
  rol?: keyof typeof RolUsuario | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
