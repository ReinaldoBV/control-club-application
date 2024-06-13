import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITorneosParticipaciones, NewTorneosParticipaciones } from '../torneos-participaciones.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITorneosParticipaciones for edit and NewTorneosParticipacionesFormGroupInput for create.
 */
type TorneosParticipacionesFormGroupInput = ITorneosParticipaciones | PartialWithRequiredKeyOf<NewTorneosParticipaciones>;

type TorneosParticipacionesFormDefaults = Pick<NewTorneosParticipaciones, 'id'>;

type TorneosParticipacionesFormGroupContent = {
  id: FormControl<ITorneosParticipaciones['id'] | NewTorneosParticipaciones['id']>;
  descripcion: FormControl<ITorneosParticipaciones['descripcion']>;
  monto: FormControl<ITorneosParticipaciones['monto']>;
  fecha: FormControl<ITorneosParticipaciones['fecha']>;
};

export type TorneosParticipacionesFormGroup = FormGroup<TorneosParticipacionesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TorneosParticipacionesFormService {
  createTorneosParticipacionesFormGroup(
    torneosParticipaciones: TorneosParticipacionesFormGroupInput = { id: null },
  ): TorneosParticipacionesFormGroup {
    const torneosParticipacionesRawValue = {
      ...this.getFormDefaults(),
      ...torneosParticipaciones,
    };
    return new FormGroup<TorneosParticipacionesFormGroupContent>({
      id: new FormControl(
        { value: torneosParticipacionesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descripcion: new FormControl(torneosParticipacionesRawValue.descripcion, {
        validators: [Validators.required],
      }),
      monto: new FormControl(torneosParticipacionesRawValue.monto, {
        validators: [Validators.required],
      }),
      fecha: new FormControl(torneosParticipacionesRawValue.fecha, {
        validators: [Validators.required],
      }),
    });
  }

  getTorneosParticipaciones(form: TorneosParticipacionesFormGroup): ITorneosParticipaciones | NewTorneosParticipaciones {
    return form.getRawValue() as ITorneosParticipaciones | NewTorneosParticipaciones;
  }

  resetForm(form: TorneosParticipacionesFormGroup, torneosParticipaciones: TorneosParticipacionesFormGroupInput): void {
    const torneosParticipacionesRawValue = { ...this.getFormDefaults(), ...torneosParticipaciones };
    form.reset(
      {
        ...torneosParticipacionesRawValue,
        id: { value: torneosParticipacionesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TorneosParticipacionesFormDefaults {
    return {
      id: null,
    };
  }
}
