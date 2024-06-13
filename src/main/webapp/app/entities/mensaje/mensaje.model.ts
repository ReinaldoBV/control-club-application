import { IJugador } from 'app/entities/jugador/jugador.model';

export interface IMensaje {
  id: number;
  remitenteId?: number | null;
  destinatarioId?: number | null;
  mensaje?: string | null;
  leido?: boolean | null;
  remitente?: Pick<IJugador, 'id'> | null;
  destinatario?: Pick<IJugador, 'id'> | null;
}

export type NewMensaje = Omit<IMensaje, 'id'> & { id: null };
