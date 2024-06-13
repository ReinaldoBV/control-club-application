import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBienes, NewBienes } from '../bienes.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBienes for edit and NewBienesFormGroupInput for create.
 */
type BienesFormGroupInput = IBienes | PartialWithRequiredKeyOf<NewBienes>;

type BienesFormDefaults = Pick<NewBienes, 'id'>;

type BienesFormGroupContent = {
  id: FormControl<IBienes['id'] | NewBienes['id']>;
  descripcion: FormControl<IBienes['descripcion']>;
  cantidad: FormControl<IBienes['cantidad']>;
  estado: FormControl<IBienes['estado']>;
  asignadoA: FormControl<IBienes['asignadoA']>;
};

export type BienesFormGroup = FormGroup<BienesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BienesFormService {
  createBienesFormGroup(bienes: BienesFormGroupInput = { id: null }): BienesFormGroup {
    const bienesRawValue = {
      ...this.getFormDefaults(),
      ...bienes,
    };
    return new FormGroup<BienesFormGroupContent>({
      id: new FormControl(
        { value: bienesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descripcion: new FormControl(bienesRawValue.descripcion, {
        validators: [Validators.required],
      }),
      cantidad: new FormControl(bienesRawValue.cantidad, {
        validators: [Validators.required],
      }),
      estado: new FormControl(bienesRawValue.estado, {
        validators: [Validators.required],
      }),
      asignadoA: new FormControl(bienesRawValue.asignadoA),
    });
  }

  getBienes(form: BienesFormGroup): IBienes | NewBienes {
    return form.getRawValue() as IBienes | NewBienes;
  }

  resetForm(form: BienesFormGroup, bienes: BienesFormGroupInput): void {
    const bienesRawValue = { ...this.getFormDefaults(), ...bienes };
    form.reset(
      {
        ...bienesRawValue,
        id: { value: bienesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): BienesFormDefaults {
    return {
      id: null,
    };
  }
}
