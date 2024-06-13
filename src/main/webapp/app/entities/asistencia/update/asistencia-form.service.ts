import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAsistencia, NewAsistencia } from '../asistencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAsistencia for edit and NewAsistenciaFormGroupInput for create.
 */
type AsistenciaFormGroupInput = IAsistencia | PartialWithRequiredKeyOf<NewAsistencia>;

type AsistenciaFormDefaults = Pick<NewAsistencia, 'id' | 'asistencia'>;

type AsistenciaFormGroupContent = {
  id: FormControl<IAsistencia['id'] | NewAsistencia['id']>;
  tipo: FormControl<IAsistencia['tipo']>;
  idEvento: FormControl<IAsistencia['idEvento']>;
  fecha: FormControl<IAsistencia['fecha']>;
  asistencia: FormControl<IAsistencia['asistencia']>;
};

export type AsistenciaFormGroup = FormGroup<AsistenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AsistenciaFormService {
  createAsistenciaFormGroup(asistencia: AsistenciaFormGroupInput = { id: null }): AsistenciaFormGroup {
    const asistenciaRawValue = {
      ...this.getFormDefaults(),
      ...asistencia,
    };
    return new FormGroup<AsistenciaFormGroupContent>({
      id: new FormControl(
        { value: asistenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(asistenciaRawValue.tipo, {
        validators: [Validators.required],
      }),
      idEvento: new FormControl(asistenciaRawValue.idEvento, {
        validators: [Validators.required],
      }),
      fecha: new FormControl(asistenciaRawValue.fecha, {
        validators: [Validators.required],
      }),
      asistencia: new FormControl(asistenciaRawValue.asistencia, {
        validators: [Validators.required],
      }),
    });
  }

  getAsistencia(form: AsistenciaFormGroup): IAsistencia | NewAsistencia {
    return form.getRawValue() as IAsistencia | NewAsistencia;
  }

  resetForm(form: AsistenciaFormGroup, asistencia: AsistenciaFormGroupInput): void {
    const asistenciaRawValue = { ...this.getFormDefaults(), ...asistencia };
    form.reset(
      {
        ...asistenciaRawValue,
        id: { value: asistenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AsistenciaFormDefaults {
    return {
      id: null,
      asistencia: false,
    };
  }
}
