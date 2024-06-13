import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnuncio, NewAnuncio } from '../anuncio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnuncio for edit and NewAnuncioFormGroupInput for create.
 */
type AnuncioFormGroupInput = IAnuncio | PartialWithRequiredKeyOf<NewAnuncio>;

type AnuncioFormDefaults = Pick<NewAnuncio, 'id'>;

type AnuncioFormGroupContent = {
  id: FormControl<IAnuncio['id'] | NewAnuncio['id']>;
  titulo: FormControl<IAnuncio['titulo']>;
  contenido: FormControl<IAnuncio['contenido']>;
  fechaPublicacion: FormControl<IAnuncio['fechaPublicacion']>;
};

export type AnuncioFormGroup = FormGroup<AnuncioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnuncioFormService {
  createAnuncioFormGroup(anuncio: AnuncioFormGroupInput = { id: null }): AnuncioFormGroup {
    const anuncioRawValue = {
      ...this.getFormDefaults(),
      ...anuncio,
    };
    return new FormGroup<AnuncioFormGroupContent>({
      id: new FormControl(
        { value: anuncioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(anuncioRawValue.titulo, {
        validators: [Validators.required],
      }),
      contenido: new FormControl(anuncioRawValue.contenido, {
        validators: [Validators.required],
      }),
      fechaPublicacion: new FormControl(anuncioRawValue.fechaPublicacion, {
        validators: [Validators.required],
      }),
    });
  }

  getAnuncio(form: AnuncioFormGroup): IAnuncio | NewAnuncio {
    return form.getRawValue() as IAnuncio | NewAnuncio;
  }

  resetForm(form: AnuncioFormGroup, anuncio: AnuncioFormGroupInput): void {
    const anuncioRawValue = { ...this.getFormDefaults(), ...anuncio };
    form.reset(
      {
        ...anuncioRawValue,
        id: { value: anuncioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnuncioFormDefaults {
    return {
      id: null,
    };
  }
}
