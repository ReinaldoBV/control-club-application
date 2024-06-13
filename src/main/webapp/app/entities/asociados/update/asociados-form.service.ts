import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAsociados, NewAsociados } from '../asociados.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAsociados for edit and NewAsociadosFormGroupInput for create.
 */
type AsociadosFormGroupInput = IAsociados | PartialWithRequiredKeyOf<NewAsociados>;

type AsociadosFormDefaults = Pick<NewAsociados, 'id'>;

type AsociadosFormGroupContent = {
  id: FormControl<IAsociados['id'] | NewAsociados['id']>;
  nombres: FormControl<IAsociados['nombres']>;
  apellidos: FormControl<IAsociados['apellidos']>;
  cargo: FormControl<IAsociados['cargo']>;
  telefono: FormControl<IAsociados['telefono']>;
  fechaAsoc: FormControl<IAsociados['fechaAsoc']>;
  email: FormControl<IAsociados['email']>;
  jugador: FormControl<IAsociados['jugador']>;
};

export type AsociadosFormGroup = FormGroup<AsociadosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AsociadosFormService {
  createAsociadosFormGroup(asociados: AsociadosFormGroupInput = { id: null }): AsociadosFormGroup {
    const asociadosRawValue = {
      ...this.getFormDefaults(),
      ...asociados,
    };
    return new FormGroup<AsociadosFormGroupContent>({
      id: new FormControl(
        { value: asociadosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombres: new FormControl(asociadosRawValue.nombres, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(asociadosRawValue.apellidos, {
        validators: [Validators.required],
      }),
      cargo: new FormControl(asociadosRawValue.cargo, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(asociadosRawValue.telefono, {
        validators: [Validators.required],
      }),
      fechaAsoc: new FormControl(asociadosRawValue.fechaAsoc, {
        validators: [Validators.required],
      }),
      email: new FormControl(asociadosRawValue.email, {
        validators: [Validators.required],
      }),
      jugador: new FormControl(asociadosRawValue.jugador),
    });
  }

  getAsociados(form: AsociadosFormGroup): IAsociados | NewAsociados {
    return form.getRawValue() as IAsociados | NewAsociados;
  }

  resetForm(form: AsociadosFormGroup, asociados: AsociadosFormGroupInput): void {
    const asociadosRawValue = { ...this.getFormDefaults(), ...asociados };
    form.reset(
      {
        ...asociadosRawValue,
        id: { value: asociadosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AsociadosFormDefaults {
    return {
      id: null,
    };
  }
}
