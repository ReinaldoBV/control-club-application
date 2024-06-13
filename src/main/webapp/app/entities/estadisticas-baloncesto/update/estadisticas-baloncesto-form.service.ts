import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEstadisticasBaloncesto, NewEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEstadisticasBaloncesto for edit and NewEstadisticasBaloncestoFormGroupInput for create.
 */
type EstadisticasBaloncestoFormGroupInput = IEstadisticasBaloncesto | PartialWithRequiredKeyOf<NewEstadisticasBaloncesto>;

type EstadisticasBaloncestoFormDefaults = Pick<NewEstadisticasBaloncesto, 'id'>;

type EstadisticasBaloncestoFormGroupContent = {
  id: FormControl<IEstadisticasBaloncesto['id'] | NewEstadisticasBaloncesto['id']>;
  puntos: FormControl<IEstadisticasBaloncesto['puntos']>;
  rebotes: FormControl<IEstadisticasBaloncesto['rebotes']>;
  asistencias: FormControl<IEstadisticasBaloncesto['asistencias']>;
  robos: FormControl<IEstadisticasBaloncesto['robos']>;
  bloqueos: FormControl<IEstadisticasBaloncesto['bloqueos']>;
  porcentajeTiro: FormControl<IEstadisticasBaloncesto['porcentajeTiro']>;
};

export type EstadisticasBaloncestoFormGroup = FormGroup<EstadisticasBaloncestoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EstadisticasBaloncestoFormService {
  createEstadisticasBaloncestoFormGroup(
    estadisticasBaloncesto: EstadisticasBaloncestoFormGroupInput = { id: null },
  ): EstadisticasBaloncestoFormGroup {
    const estadisticasBaloncestoRawValue = {
      ...this.getFormDefaults(),
      ...estadisticasBaloncesto,
    };
    return new FormGroup<EstadisticasBaloncestoFormGroupContent>({
      id: new FormControl(
        { value: estadisticasBaloncestoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      puntos: new FormControl(estadisticasBaloncestoRawValue.puntos),
      rebotes: new FormControl(estadisticasBaloncestoRawValue.rebotes),
      asistencias: new FormControl(estadisticasBaloncestoRawValue.asistencias),
      robos: new FormControl(estadisticasBaloncestoRawValue.robos),
      bloqueos: new FormControl(estadisticasBaloncestoRawValue.bloqueos),
      porcentajeTiro: new FormControl(estadisticasBaloncestoRawValue.porcentajeTiro),
    });
  }

  getEstadisticasBaloncesto(form: EstadisticasBaloncestoFormGroup): IEstadisticasBaloncesto | NewEstadisticasBaloncesto {
    return form.getRawValue() as IEstadisticasBaloncesto | NewEstadisticasBaloncesto;
  }

  resetForm(form: EstadisticasBaloncestoFormGroup, estadisticasBaloncesto: EstadisticasBaloncestoFormGroupInput): void {
    const estadisticasBaloncestoRawValue = { ...this.getFormDefaults(), ...estadisticasBaloncesto };
    form.reset(
      {
        ...estadisticasBaloncestoRawValue,
        id: { value: estadisticasBaloncestoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EstadisticasBaloncestoFormDefaults {
    return {
      id: null,
    };
  }
}
