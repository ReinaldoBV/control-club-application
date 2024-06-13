export interface IMensaje {
  id: number;
  remitenteId?: number | null;
  destinatarioId?: number | null;
  mensaje?: string | null;
  leido?: boolean | null;
}

export type NewMensaje = Omit<IMensaje, 'id'> & { id: null };
