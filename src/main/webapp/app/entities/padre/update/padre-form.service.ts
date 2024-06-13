import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPadre, NewPadre } from '../padre.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPadre for edit and NewPadreFormGroupInput for create.
 */
type PadreFormGroupInput = IPadre | PartialWithRequiredKeyOf<NewPadre>;

type PadreFormDefaults = Pick<NewPadre, 'id' | 'asociado'>;

type PadreFormGroupContent = {
  id: FormControl<IPadre['id'] | NewPadre['id']>;
  nombres: FormControl<IPadre['nombres']>;
  apellidos: FormControl<IPadre['apellidos']>;
  relacion: FormControl<IPadre['relacion']>;
  telefono: FormControl<IPadre['telefono']>;
  asociado: FormControl<IPadre['asociado']>;
  email: FormControl<IPadre['email']>;
};

export type PadreFormGroup = FormGroup<PadreFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PadreFormService {
  createPadreFormGroup(padre: PadreFormGroupInput = { id: null }): PadreFormGroup {
    const padreRawValue = {
      ...this.getFormDefaults(),
      ...padre,
    };
    return new FormGroup<PadreFormGroupContent>({
      id: new FormControl(
        { value: padreRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombres: new FormControl(padreRawValue.nombres, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(padreRawValue.apellidos, {
        validators: [Validators.required],
      }),
      relacion: new FormControl(padreRawValue.relacion, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(padreRawValue.telefono, {
        validators: [Validators.required],
      }),
      asociado: new FormControl(padreRawValue.asociado, {
        validators: [Validators.required],
      }),
      email: new FormControl(padreRawValue.email, {
        validators: [Validators.required],
      }),
    });
  }

  getPadre(form: PadreFormGroup): IPadre | NewPadre {
    return form.getRawValue() as IPadre | NewPadre;
  }

  resetForm(form: PadreFormGroup, padre: PadreFormGroupInput): void {
    const padreRawValue = { ...this.getFormDefaults(), ...padre };
    form.reset(
      {
        ...padreRawValue,
        id: { value: padreRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PadreFormDefaults {
    return {
      id: null,
      asociado: false,
    };
  }
}
