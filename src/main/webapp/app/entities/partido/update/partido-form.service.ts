import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPartido, NewPartido } from '../partido.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPartido for edit and NewPartidoFormGroupInput for create.
 */
type PartidoFormGroupInput = IPartido | PartialWithRequiredKeyOf<NewPartido>;

type PartidoFormDefaults = Pick<NewPartido, 'id'>;

type PartidoFormGroupContent = {
  id: FormControl<IPartido['id'] | NewPartido['id']>;
  fecha: FormControl<IPartido['fecha']>;
  oponente: FormControl<IPartido['oponente']>;
  resultado: FormControl<IPartido['resultado']>;
};

export type PartidoFormGroup = FormGroup<PartidoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PartidoFormService {
  createPartidoFormGroup(partido: PartidoFormGroupInput = { id: null }): PartidoFormGroup {
    const partidoRawValue = {
      ...this.getFormDefaults(),
      ...partido,
    };
    return new FormGroup<PartidoFormGroupContent>({
      id: new FormControl(
        { value: partidoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fecha: new FormControl(partidoRawValue.fecha, {
        validators: [Validators.required],
      }),
      oponente: new FormControl(partidoRawValue.oponente, {
        validators: [Validators.required],
      }),
      resultado: new FormControl(partidoRawValue.resultado),
    });
  }

  getPartido(form: PartidoFormGroup): IPartido | NewPartido {
    return form.getRawValue() as IPartido | NewPartido;
  }

  resetForm(form: PartidoFormGroup, partido: PartidoFormGroupInput): void {
    const partidoRawValue = { ...this.getFormDefaults(), ...partido };
    form.reset(
      {
        ...partidoRawValue,
        id: { value: partidoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PartidoFormDefaults {
    return {
      id: null,
    };
  }
}
