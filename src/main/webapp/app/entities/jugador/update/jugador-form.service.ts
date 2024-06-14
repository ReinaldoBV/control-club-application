import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IJugador, NewJugador } from '../jugador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IJugador for edit and NewJugadorFormGroupInput for create.
 */
type JugadorFormGroupInput = IJugador | PartialWithRequiredKeyOf<NewJugador>;

type JugadorFormDefaults = Pick<NewJugador, 'id'>;

type JugadorFormGroupContent = {
  id: FormControl<IJugador['id'] | NewJugador['id']>;
  nroIdentificacion: FormControl<IJugador['nroIdentificacion']>;
  tipoIdentificacion: FormControl<IJugador['tipoIdentificacion']>;
  nombres: FormControl<IJugador['nombres']>;
  apellidos: FormControl<IJugador['apellidos']>;
  nacionalidad: FormControl<IJugador['nacionalidad']>;
  edad: FormControl<IJugador['edad']>;
  fechaNacimiento: FormControl<IJugador['fechaNacimiento']>;
  numeroCamisa: FormControl<IJugador['numeroCamisa']>;
  contactoEmergencia: FormControl<IJugador['contactoEmergencia']>;
  calleAvenidaDireccion: FormControl<IJugador['calleAvenidaDireccion']>;
  numeroDireccion: FormControl<IJugador['numeroDireccion']>;
  numeroPersonal: FormControl<IJugador['numeroPersonal']>;
  imagenJugador: FormControl<IJugador['imagenJugador']>;
  imagenJugadorContentType: FormControl<IJugador['imagenJugadorContentType']>;
  documentoIdentificacion: FormControl<IJugador['documentoIdentificacion']>;
  documentoIdentificacionContentType: FormControl<IJugador['documentoIdentificacionContentType']>;
  centroSalud: FormControl<IJugador['centroSalud']>;
  previsionSalud: FormControl<IJugador['previsionSalud']>;
  centroEducativo: FormControl<IJugador['centroEducativo']>;
  categorias: FormControl<IJugador['categorias']>;
};

export type JugadorFormGroup = FormGroup<JugadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class JugadorFormService {
  createJugadorFormGroup(jugador: JugadorFormGroupInput = { id: null }): JugadorFormGroup {
    const jugadorRawValue = {
      ...this.getFormDefaults(),
      ...jugador,
    };
    return new FormGroup<JugadorFormGroupContent>({
      id: new FormControl(
        { value: jugadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nroIdentificacion: new FormControl(jugadorRawValue.nroIdentificacion, {
        validators: [Validators.required],
      }),
      tipoIdentificacion: new FormControl(jugadorRawValue.tipoIdentificacion, {
        validators: [Validators.required],
      }),
      nombres: new FormControl(jugadorRawValue.nombres, {
        validators: [Validators.required],
      }),
      apellidos: new FormControl(jugadorRawValue.apellidos, {
        validators: [Validators.required],
      }),
      nacionalidad: new FormControl(jugadorRawValue.nacionalidad, {
        validators: [Validators.required],
      }),
      edad: new FormControl(jugadorRawValue.edad, {
        validators: [Validators.required],
      }),
      fechaNacimiento: new FormControl(jugadorRawValue.fechaNacimiento, {
        validators: [Validators.required],
      }),
      numeroCamisa: new FormControl(jugadorRawValue.numeroCamisa, {
        validators: [Validators.required],
      }),
      contactoEmergencia: new FormControl(jugadorRawValue.contactoEmergencia, {
        validators: [Validators.required],
      }),
      calleAvenidaDireccion: new FormControl(jugadorRawValue.calleAvenidaDireccion, {
        validators: [Validators.required],
      }),
      numeroDireccion: new FormControl(jugadorRawValue.numeroDireccion, {
        validators: [Validators.required],
      }),
      numeroPersonal: new FormControl(jugadorRawValue.numeroPersonal, {
        validators: [Validators.required],
      }),
      imagenJugador: new FormControl(jugadorRawValue.imagenJugador),
      imagenJugadorContentType: new FormControl(jugadorRawValue.imagenJugadorContentType),
      documentoIdentificacion: new FormControl(jugadorRawValue.documentoIdentificacion),
      documentoIdentificacionContentType: new FormControl(jugadorRawValue.documentoIdentificacionContentType),
      centroSalud: new FormControl(jugadorRawValue.centroSalud),
      previsionSalud: new FormControl(jugadorRawValue.previsionSalud),
      centroEducativo: new FormControl(jugadorRawValue.centroEducativo),
      categorias: new FormControl(jugadorRawValue.categorias),
    });
  }

  getJugador(form: JugadorFormGroup): IJugador | NewJugador {
    return form.getRawValue() as IJugador | NewJugador;
  }

  resetForm(form: JugadorFormGroup, jugador: JugadorFormGroupInput): void {
    const jugadorRawValue = { ...this.getFormDefaults(), ...jugador };
    form.reset(
      {
        ...jugadorRawValue,
        id: { value: jugadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): JugadorFormDefaults {
    return {
      id: null,
    };
  }
}
