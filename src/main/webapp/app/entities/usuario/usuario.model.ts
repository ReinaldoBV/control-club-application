import { IJugador } from 'app/entities/jugador/jugador.model';
import { IAsociados } from 'app/entities/asociados/asociados.model';
import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';
import { RolUsuario } from 'app/entities/enumerations/rol-usuario.model';

export interface IUsuario {
  id: number;
  username?: string | null;
  password?: string | null;
  rol?: keyof typeof RolUsuario | null;
  jugador?: Pick<IJugador, 'id'> | null;
  asociados?: Pick<IAsociados, 'id'> | null;
  directivos?: Pick<IDirectivos, 'id'> | null;
  cuerpoTecnico?: Pick<ICuerpoTecnico, 'id'> | null;
}

export type NewUsuario = Omit<IUsuario, 'id'> & { id: null };
