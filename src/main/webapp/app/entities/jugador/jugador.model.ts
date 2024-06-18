import dayjs from 'dayjs/esm';
import { TipoIdentificacion } from 'app/entities/enumerations/tipo-identificacion.model';
import { Nacionalidad } from 'app/entities/enumerations/nacionalidad.model';

export interface IJugador {
  id: number;
  idJugador?: number | null;
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
}

export type NewJugador = Omit<IJugador, 'id'> & { id: null };
