import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IClub, NewClub } from '../club.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IClub for edit and NewClubFormGroupInput for create.
 */
type ClubFormGroupInput = IClub | PartialWithRequiredKeyOf<NewClub>;

type ClubFormDefaults = Pick<NewClub, 'id'>;

type ClubFormGroupContent = {
  id: FormControl<IClub['id'] | NewClub['id']>;
  razon: FormControl<IClub['razon']>;
  direccion: FormControl<IClub['direccion']>;
  telefono: FormControl<IClub['telefono']>;
  email: FormControl<IClub['email']>;
  fechaFundacion: FormControl<IClub['fechaFundacion']>;
  presidente: FormControl<IClub['presidente']>;
};

export type ClubFormGroup = FormGroup<ClubFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ClubFormService {
  createClubFormGroup(club: ClubFormGroupInput = { id: null }): ClubFormGroup {
    const clubRawValue = {
      ...this.getFormDefaults(),
      ...club,
    };
    return new FormGroup<ClubFormGroupContent>({
      id: new FormControl(
        { value: clubRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      razon: new FormControl(clubRawValue.razon, {
        validators: [Validators.required],
      }),
      direccion: new FormControl(clubRawValue.direccion, {
        validators: [Validators.required],
      }),
      telefono: new FormControl(clubRawValue.telefono, {
        validators: [Validators.required],
      }),
      email: new FormControl(clubRawValue.email, {
        validators: [Validators.required],
      }),
      fechaFundacion: new FormControl(clubRawValue.fechaFundacion, {
        validators: [Validators.required],
      }),
      presidente: new FormControl(clubRawValue.presidente, {
        validators: [Validators.required],
      }),
    });
  }

  getClub(form: ClubFormGroup): IClub | NewClub {
    return form.getRawValue() as IClub | NewClub;
  }

  resetForm(form: ClubFormGroup, club: ClubFormGroupInput): void {
    const clubRawValue = { ...this.getFormDefaults(), ...club };
    form.reset(
      {
        ...clubRawValue,
        id: { value: clubRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ClubFormDefaults {
    return {
      id: null,
    };
  }
}
