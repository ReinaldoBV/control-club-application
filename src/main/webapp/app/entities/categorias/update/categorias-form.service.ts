import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICategorias, NewCategorias } from '../categorias.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICategorias for edit and NewCategoriasFormGroupInput for create.
 */
type CategoriasFormGroupInput = ICategorias | PartialWithRequiredKeyOf<NewCategorias>;

type CategoriasFormDefaults = Pick<NewCategorias, 'id'>;

type CategoriasFormGroupContent = {
  id: FormControl<ICategorias['id'] | NewCategorias['id']>;
  anioInicio: FormControl<ICategorias['anioInicio']>;
  anioFinal: FormControl<ICategorias['anioFinal']>;
  nombreCategoria: FormControl<ICategorias['nombreCategoria']>;
};

export type CategoriasFormGroup = FormGroup<CategoriasFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CategoriasFormService {
  createCategoriasFormGroup(categorias: CategoriasFormGroupInput = { id: null }): CategoriasFormGroup {
    const categoriasRawValue = {
      ...this.getFormDefaults(),
      ...categorias,
    };
    return new FormGroup<CategoriasFormGroupContent>({
      id: new FormControl(
        { value: categoriasRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      anioInicio: new FormControl(categoriasRawValue.anioInicio, {
        validators: [Validators.required],
      }),
      anioFinal: new FormControl(categoriasRawValue.anioFinal, {
        validators: [Validators.required],
      }),
      nombreCategoria: new FormControl(categoriasRawValue.nombreCategoria, {
        validators: [Validators.required],
      }),
    });
  }

  getCategorias(form: CategoriasFormGroup): ICategorias | NewCategorias {
    return form.getRawValue() as ICategorias | NewCategorias;
  }

  resetForm(form: CategoriasFormGroup, categorias: CategoriasFormGroupInput): void {
    const categoriasRawValue = { ...this.getFormDefaults(), ...categorias };
    form.reset(
      {
        ...categoriasRawValue,
        id: { value: categoriasRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CategoriasFormDefaults {
    return {
      id: null,
    };
  }
}
