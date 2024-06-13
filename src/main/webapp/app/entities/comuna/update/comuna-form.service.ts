import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IComuna, NewComuna } from '../comuna.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IComuna for edit and NewComunaFormGroupInput for create.
 */
type ComunaFormGroupInput = IComuna | PartialWithRequiredKeyOf<NewComuna>;

type ComunaFormDefaults = Pick<NewComuna, 'id'>;

type ComunaFormGroupContent = {
  id: FormControl<IComuna['id'] | NewComuna['id']>;
  comuna: FormControl<IComuna['comuna']>;
};

export type ComunaFormGroup = FormGroup<ComunaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ComunaFormService {
  createComunaFormGroup(comuna: ComunaFormGroupInput = { id: null }): ComunaFormGroup {
    const comunaRawValue = {
      ...this.getFormDefaults(),
      ...comuna,
    };
    return new FormGroup<ComunaFormGroupContent>({
      id: new FormControl(
        { value: comunaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      comuna: new FormControl(comunaRawValue.comuna, {
        validators: [Validators.required],
      }),
    });
  }

  getComuna(form: ComunaFormGroup): IComuna | NewComuna {
    return form.getRawValue() as IComuna | NewComuna;
  }

  resetForm(form: ComunaFormGroup, comuna: ComunaFormGroupInput): void {
    const comunaRawValue = { ...this.getFormDefaults(), ...comuna };
    form.reset(
      {
        ...comunaRawValue,
        id: { value: comunaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ComunaFormDefaults {
    return {
      id: null,
    };
  }
}
