import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICentroEducativo, NewCentroEducativo } from '../centro-educativo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICentroEducativo for edit and NewCentroEducativoFormGroupInput for create.
 */
type CentroEducativoFormGroupInput = ICentroEducativo | PartialWithRequiredKeyOf<NewCentroEducativo>;

type CentroEducativoFormDefaults = Pick<NewCentroEducativo, 'id'>;

type CentroEducativoFormGroupContent = {
  id: FormControl<ICentroEducativo['id'] | NewCentroEducativo['id']>;
  centroEducativo: FormControl<ICentroEducativo['centroEducativo']>;
};

export type CentroEducativoFormGroup = FormGroup<CentroEducativoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CentroEducativoFormService {
  createCentroEducativoFormGroup(centroEducativo: CentroEducativoFormGroupInput = { id: null }): CentroEducativoFormGroup {
    const centroEducativoRawValue = {
      ...this.getFormDefaults(),
      ...centroEducativo,
    };
    return new FormGroup<CentroEducativoFormGroupContent>({
      id: new FormControl(
        { value: centroEducativoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      centroEducativo: new FormControl(centroEducativoRawValue.centroEducativo, {
        validators: [Validators.required],
      }),
    });
  }

  getCentroEducativo(form: CentroEducativoFormGroup): ICentroEducativo | NewCentroEducativo {
    return form.getRawValue() as ICentroEducativo | NewCentroEducativo;
  }

  resetForm(form: CentroEducativoFormGroup, centroEducativo: CentroEducativoFormGroupInput): void {
    const centroEducativoRawValue = { ...this.getFormDefaults(), ...centroEducativo };
    form.reset(
      {
        ...centroEducativoRawValue,
        id: { value: centroEducativoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CentroEducativoFormDefaults {
    return {
      id: null,
    };
  }
}
