import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEntrenamiento, NewEntrenamiento } from '../entrenamiento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEntrenamiento for edit and NewEntrenamientoFormGroupInput for create.
 */
type EntrenamientoFormGroupInput = IEntrenamiento | PartialWithRequiredKeyOf<NewEntrenamiento>;

type EntrenamientoFormDefaults = Pick<NewEntrenamiento, 'id'>;

type EntrenamientoFormGroupContent = {
  id: FormControl<IEntrenamiento['id'] | NewEntrenamiento['id']>;
  fechaHora: FormControl<IEntrenamiento['fechaHora']>;
  duracion: FormControl<IEntrenamiento['duracion']>;
  cuerpoTecnico: FormControl<IEntrenamiento['cuerpoTecnico']>;
};

export type EntrenamientoFormGroup = FormGroup<EntrenamientoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EntrenamientoFormService {
  createEntrenamientoFormGroup(entrenamiento: EntrenamientoFormGroupInput = { id: null }): EntrenamientoFormGroup {
    const entrenamientoRawValue = {
      ...this.getFormDefaults(),
      ...entrenamiento,
    };
    return new FormGroup<EntrenamientoFormGroupContent>({
      id: new FormControl(
        { value: entrenamientoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fechaHora: new FormControl(entrenamientoRawValue.fechaHora, {
        validators: [Validators.required],
      }),
      duracion: new FormControl(entrenamientoRawValue.duracion, {
        validators: [Validators.required],
      }),
      cuerpoTecnico: new FormControl(entrenamientoRawValue.cuerpoTecnico),
    });
  }

  getEntrenamiento(form: EntrenamientoFormGroup): IEntrenamiento | NewEntrenamiento {
    return form.getRawValue() as IEntrenamiento | NewEntrenamiento;
  }

  resetForm(form: EntrenamientoFormGroup, entrenamiento: EntrenamientoFormGroupInput): void {
    const entrenamientoRawValue = { ...this.getFormDefaults(), ...entrenamiento };
    form.reset(
      {
        ...entrenamientoRawValue,
        id: { value: entrenamientoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EntrenamientoFormDefaults {
    return {
      id: null,
    };
  }
}
