import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPrevisionSalud, NewPrevisionSalud } from '../prevision-salud.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPrevisionSalud for edit and NewPrevisionSaludFormGroupInput for create.
 */
type PrevisionSaludFormGroupInput = IPrevisionSalud | PartialWithRequiredKeyOf<NewPrevisionSalud>;

type PrevisionSaludFormDefaults = Pick<NewPrevisionSalud, 'id'>;

type PrevisionSaludFormGroupContent = {
  id: FormControl<IPrevisionSalud['id'] | NewPrevisionSalud['id']>;
  tipoPrevision: FormControl<IPrevisionSalud['tipoPrevision']>;
};

export type PrevisionSaludFormGroup = FormGroup<PrevisionSaludFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PrevisionSaludFormService {
  createPrevisionSaludFormGroup(previsionSalud: PrevisionSaludFormGroupInput = { id: null }): PrevisionSaludFormGroup {
    const previsionSaludRawValue = {
      ...this.getFormDefaults(),
      ...previsionSalud,
    };
    return new FormGroup<PrevisionSaludFormGroupContent>({
      id: new FormControl(
        { value: previsionSaludRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipoPrevision: new FormControl(previsionSaludRawValue.tipoPrevision, {
        validators: [Validators.required],
      }),
    });
  }

  getPrevisionSalud(form: PrevisionSaludFormGroup): IPrevisionSalud | NewPrevisionSalud {
    return form.getRawValue() as IPrevisionSalud | NewPrevisionSalud;
  }

  resetForm(form: PrevisionSaludFormGroup, previsionSalud: PrevisionSaludFormGroupInput): void {
    const previsionSaludRawValue = { ...this.getFormDefaults(), ...previsionSalud };
    form.reset(
      {
        ...previsionSaludRawValue,
        id: { value: previsionSaludRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PrevisionSaludFormDefaults {
    return {
      id: null,
    };
  }
}
