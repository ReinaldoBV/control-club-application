import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFinanzasEgreso, NewFinanzasEgreso } from '../finanzas-egreso.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFinanzasEgreso for edit and NewFinanzasEgresoFormGroupInput for create.
 */
type FinanzasEgresoFormGroupInput = IFinanzasEgreso | PartialWithRequiredKeyOf<NewFinanzasEgreso>;

type FinanzasEgresoFormDefaults = Pick<NewFinanzasEgreso, 'id'>;

type FinanzasEgresoFormGroupContent = {
  id: FormControl<IFinanzasEgreso['id'] | NewFinanzasEgreso['id']>;
  tipo: FormControl<IFinanzasEgreso['tipo']>;
  descripcion: FormControl<IFinanzasEgreso['descripcion']>;
  monto: FormControl<IFinanzasEgreso['monto']>;
  fecha: FormControl<IFinanzasEgreso['fecha']>;
  cuentas: FormControl<IFinanzasEgreso['cuentas']>;
};

export type FinanzasEgresoFormGroup = FormGroup<FinanzasEgresoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FinanzasEgresoFormService {
  createFinanzasEgresoFormGroup(finanzasEgreso: FinanzasEgresoFormGroupInput = { id: null }): FinanzasEgresoFormGroup {
    const finanzasEgresoRawValue = {
      ...this.getFormDefaults(),
      ...finanzasEgreso,
    };
    return new FormGroup<FinanzasEgresoFormGroupContent>({
      id: new FormControl(
        { value: finanzasEgresoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(finanzasEgresoRawValue.tipo, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(finanzasEgresoRawValue.descripcion, {
        validators: [Validators.required],
      }),
      monto: new FormControl(finanzasEgresoRawValue.monto, {
        validators: [Validators.required],
      }),
      fecha: new FormControl(finanzasEgresoRawValue.fecha, {
        validators: [Validators.required],
      }),
      cuentas: new FormControl(finanzasEgresoRawValue.cuentas),
    });
  }

  getFinanzasEgreso(form: FinanzasEgresoFormGroup): IFinanzasEgreso | NewFinanzasEgreso {
    return form.getRawValue() as IFinanzasEgreso | NewFinanzasEgreso;
  }

  resetForm(form: FinanzasEgresoFormGroup, finanzasEgreso: FinanzasEgresoFormGroupInput): void {
    const finanzasEgresoRawValue = { ...this.getFormDefaults(), ...finanzasEgreso };
    form.reset(
      {
        ...finanzasEgresoRawValue,
        id: { value: finanzasEgresoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FinanzasEgresoFormDefaults {
    return {
      id: null,
    };
  }
}
