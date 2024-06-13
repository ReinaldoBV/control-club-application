import dayjs from 'dayjs/esm';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { IPrevisionSalud } from 'app/entities/prevision-salud/prevision-salud.model';
import { IComuna } from 'app/entities/comuna/comuna.model';
import { ICentroEducativo } from 'app/entities/centro-educativo/centro-educativo.model';
import { ICategorias } from 'app/entities/categorias/categorias.model';
import { TipoIdentificacion } from 'app/entities/enumerations/tipo-identificacion.model';
import { Nacionalidad } from 'app/entities/enumerations/nacionalidad.model';

export interface IJugador {
  id: number;
  nroIdentificacion?: number | null;
  tipoIdentificacion?: keyof typeof TipoIdentificacion | null;
  nombres?: string | null;
  apellidos?: string | null;
  nacionalidad?: keyof typeof Nacionalidad | null;
  edad?: number | null;
  fechaNacimiento?: dayjs.Dayjs | null;
  numeroCamisa?: number | null;
  contactoEmergencia?: string | null;
  calleAvenidaDireccion?: string | null;
  numeroDireccion?: number | null;
  numeroPersonal?: number | null;
  imagenJugador?: string | null;
  imagenJugadorContentType?: string | null;
  documentoIdentificacion?: string | null;
  documentoIdentificacionContentType?: string | null;
  centroSalud?: Pick<ICentroSalud, 'id'> | null;
  previsionSalud?: Pick<IPrevisionSalud, 'id'> | null;
  comuna?: Pick<IComuna, 'id'> | null;
  centroEducativo?: Pick<ICentroEducativo, 'id'> | null;
  categorias?: Pick<ICategorias, 'id'> | null;
}

export type NewJugador = Omit<IJugador, 'id'> & { id: null };
