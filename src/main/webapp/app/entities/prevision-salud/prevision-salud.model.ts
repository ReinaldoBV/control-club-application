import { TipoPrevision } from 'app/entities/enumerations/tipo-prevision.model';

export interface IPrevisionSalud {
  id: number;
  tipoPrevision?: keyof typeof TipoPrevision | null;
}

export type NewPrevisionSalud = Omit<IPrevisionSalud, 'id'> & { id: null };
