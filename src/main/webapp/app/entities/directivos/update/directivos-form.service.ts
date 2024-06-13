import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDirectivos, NewDirectivos } from '../directivos.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDirectivos for edit and NewDirectivosFormGroupInput for create.
 */
type DirectivosFormGroupInput = IDirectivos | PartialWithRequiredKeyOf<NewDirectivos>;

type DirectivosFormDefaults = Pick<NewDirectivos, 'id'>;

type DirectivosFormGroupContent = {
  id: FormControl<IDirectivos['id'] | NewDirectivos['id']>;
  nombres: FormControl<IDirectivos['nombres']>;
  apellidos: FormControl<IDirectivos['apellidos']>;
  cargo: FormControl<IDirectivos['cargo']>;
  telefono: FormControl<IDirectivos['telefono']>;
  fechaEleccion: FormControl<IDirectivos['fechaEleccion']>;
  fechaVencimiento: FormControl<IDirectivos['fechaVencimiento']>;
  email: FormControl<IDirectivos['email']>;
};

export type DirectivosFormGroup = FormGroup<DirectivosFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DirectivosFormService {
  createDirectivosFormGroup(directivos: DirectivosFormGroupInput = { id: null }): DirectivosFormGroup {
    const directivosRawValue = {
      ...this.getFormDefaults(),
      ...directivos,
    };
    return new FormGroup<DirectivosFormGroupContent>({
      id: new FormControl(
        { value: directivosRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombres: new FormControl(directivosRawValue.nombres, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(directivosRawValue.apellidos, {
        validators: [Validators.required],
      }),
      cargo: new FormControl(directivosRawValue.cargo, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(directivosRawValue.telefono, {
        validators: [Validators.required],
      }),
      fechaEleccion: new FormControl(directivosRawValue.fechaEleccion, {
        validators: [Validators.required],
      }),
      fechaVencimiento: new FormControl(directivosRawValue.fechaVencimiento, {
        validators: [Validators.required],
      }),
      email: new FormControl(directivosRawValue.email, {
        validators: [Validators.required],
      }),
    });
  }

  getDirectivos(form: DirectivosFormGroup): IDirectivos | NewDirectivos {
    return form.getRawValue() as IDirectivos | NewDirectivos;
  }

  resetForm(form: DirectivosFormGroup, directivos: DirectivosFormGroupInput): void {
    const directivosRawValue = { ...this.getFormDefaults(), ...directivos };
    form.reset(
      {
        ...directivosRawValue,
        id: { value: directivosRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DirectivosFormDefaults {
    return {
      id: null,
    };
  }
}
