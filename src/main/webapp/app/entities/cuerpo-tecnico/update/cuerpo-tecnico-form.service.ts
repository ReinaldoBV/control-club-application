import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICuerpoTecnico, NewCuerpoTecnico } from '../cuerpo-tecnico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICuerpoTecnico for edit and NewCuerpoTecnicoFormGroupInput for create.
 */
type CuerpoTecnicoFormGroupInput = ICuerpoTecnico | PartialWithRequiredKeyOf<NewCuerpoTecnico>;

type CuerpoTecnicoFormDefaults = Pick<NewCuerpoTecnico, 'id'>;

type CuerpoTecnicoFormGroupContent = {
  id: FormControl<ICuerpoTecnico['id'] | NewCuerpoTecnico['id']>;
  nombres: FormControl<ICuerpoTecnico['nombres']>;
  apellidos: FormControl<ICuerpoTecnico['apellidos']>;
  rolTecnico: FormControl<ICuerpoTecnico['rolTecnico']>;
  telefono: FormControl<ICuerpoTecnico['telefono']>;
  fechaInicio: FormControl<ICuerpoTecnico['fechaInicio']>;
  email: FormControl<ICuerpoTecnico['email']>;
  asociados: FormControl<ICuerpoTecnico['asociados']>;
};

export type CuerpoTecnicoFormGroup = FormGroup<CuerpoTecnicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CuerpoTecnicoFormService {
  createCuerpoTecnicoFormGroup(cuerpoTecnico: CuerpoTecnicoFormGroupInput = { id: null }): CuerpoTecnicoFormGroup {
    const cuerpoTecnicoRawValue = {
      ...this.getFormDefaults(),
      ...cuerpoTecnico,
    };
    return new FormGroup<CuerpoTecnicoFormGroupContent>({
      id: new FormControl(
        { value: cuerpoTecnicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nombres: new FormControl(cuerpoTecnicoRawValue.nombres, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(cuerpoTecnicoRawValue.apellidos, {
        validators: [Validators.required],
      }),
      rolTecnico: new FormControl(cuerpoTecnicoRawValue.rolTecnico, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(cuerpoTecnicoRawValue.telefono, {
        validators: [Validators.required],
      }),
      fechaInicio: new FormControl(cuerpoTecnicoRawValue.fechaInicio, {
        validators: [Validators.required],
      }),
      email: new FormControl(cuerpoTecnicoRawValue.email, {
        validators: [Validators.required],
      }),
      asociados: new FormControl(cuerpoTecnicoRawValue.asociados),
    });
  }

  getCuerpoTecnico(form: CuerpoTecnicoFormGroup): ICuerpoTecnico | NewCuerpoTecnico {
    return form.getRawValue() as ICuerpoTecnico | NewCuerpoTecnico;
  }

  resetForm(form: CuerpoTecnicoFormGroup, cuerpoTecnico: CuerpoTecnicoFormGroupInput): void {
    const cuerpoTecnicoRawValue = { ...this.getFormDefaults(), ...cuerpoTecnico };
    form.reset(
      {
        ...cuerpoTecnicoRawValue,
        id: { value: cuerpoTecnicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CuerpoTecnicoFormDefaults {
    return {
      id: null,
    };
  }
}
