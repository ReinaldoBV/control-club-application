import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICuentas, NewCuentas } from '../cuentas.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICuentas for edit and NewCuentasFormGroupInput for create.
 */
type CuentasFormGroupInput = ICuentas | PartialWithRequiredKeyOf<NewCuentas>;

type CuentasFormDefaults = Pick<NewCuentas, 'id'>;

type CuentasFormGroupContent = {
  id: FormControl<ICuentas['id'] | NewCuentas['id']>;
  tipo: FormControl<ICuentas['tipo']>;
  descripcion: FormControl<ICuentas['descripcion']>;
  monto: FormControl<ICuentas['monto']>;
  nroCuotas: FormControl<ICuentas['nroCuotas']>;
  fechaVencimiento: FormControl<ICuentas['fechaVencimiento']>;
  estado: FormControl<ICuentas['estado']>;
};

export type CuentasFormGroup = FormGroup<CuentasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CuentasFormService {
  createCuentasFormGroup(cuentas: CuentasFormGroupInput = { id: null }): CuentasFormGroup {
    const cuentasRawValue = {
      ...this.getFormDefaults(),
      ...cuentas,
    };
    return new FormGroup<CuentasFormGroupContent>({
      id: new FormControl(
        { value: cuentasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      tipo: new FormControl(cuentasRawValue.tipo, {
        validators: [Validators.required],
      }),
      descripcion: new FormControl(cuentasRawValue.descripcion, {
        validators: [Validators.required],
      }),
      monto: new FormControl(cuentasRawValue.monto, {
        validators: [Validators.required],
      }),
      nroCuotas: new FormControl(cuentasRawValue.nroCuotas, {
        validators: [Validators.required],
      }),
      fechaVencimiento: new FormControl(cuentasRawValue.fechaVencimiento, {
        validators: [Validators.required],
      }),
      estado: new FormControl(cuentasRawValue.estado, {
        validators: [Validators.required],
      }),
    });
  }

  getCuentas(form: CuentasFormGroup): ICuentas | NewCuentas {
    return form.getRawValue() as ICuentas | NewCuentas;
  }

  resetForm(form: CuentasFormGroup, cuentas: CuentasFormGroupInput): void {
    const cuentasRawValue = { ...this.getFormDefaults(), ...cuentas };
    form.reset(
      {
        ...cuentasRawValue,
        id: { value: cuentasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CuentasFormDefaults {
    return {
      id: null,
    };
  }
}
