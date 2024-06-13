import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFinanzasIngreso, NewFinanzasIngreso } from '../finanzas-ingreso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFinanzasIngreso for edit and NewFinanzasIngresoFormGroupInput for create.
 */
type FinanzasIngresoFormGroupInput = IFinanzasIngreso | PartialWithRequiredKeyOf<NewFinanzasIngreso>;

type FinanzasIngresoFormDefaults = Pick<NewFinanzasIngreso, 'id'>;

type FinanzasIngresoFormGroupContent = {
  id: FormControl<IFinanzasIngreso['id'] | NewFinanzasIngreso['id']>;
  tipo: FormControl<IFinanzasIngreso['tipo']>;
  descripcion: FormControl<IFinanzasIngreso['descripcion']>;
  monto: FormControl<IFinanzasIngreso['monto']>;
  fecha: FormControl<IFinanzasIngreso['fecha']>;
};

export type FinanzasIngresoFormGroup = FormGroup<FinanzasIngresoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FinanzasIngresoFormService {
  createFinanzasIngresoFormGroup(finanzasIngreso: FinanzasIngresoFormGroupInput = { id: null }): FinanzasIngresoFormGroup {
    const finanzasIngresoRawValue = {
      ...this.getFormDefaults(),
      ...finanzasIngreso,
    };
    return new FormGroup<FinanzasIngresoFormGroupContent>({
      id: new FormControl(
        { value: finanzasIngresoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(finanzasIngresoRawValue.tipo, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(finanzasIngresoRawValue.descripcion, {
        validators: [Validators.required],
      }),
      monto: new FormControl(finanzasIngresoRawValue.monto, {
        validators: [Validators.required],
      }),
      fecha: new FormControl(finanzasIngresoRawValue.fecha, {
        validators: [Validators.required],
      }),
    });
  }

  getFinanzasIngreso(form: FinanzasIngresoFormGroup): IFinanzasIngreso | NewFinanzasIngreso {
    return form.getRawValue() as IFinanzasIngreso | NewFinanzasIngreso;
  }

  resetForm(form: FinanzasIngresoFormGroup, finanzasIngreso: FinanzasIngresoFormGroupInput): void {
    const finanzasIngresoRawValue = { ...this.getFormDefaults(), ...finanzasIngreso };
    form.reset(
      {
        ...finanzasIngresoRawValue,
        id: { value: finanzasIngresoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FinanzasIngresoFormDefaults {
    return {
      id: null,
    };
  }
}
