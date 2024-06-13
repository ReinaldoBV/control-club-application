import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../club.test-samples';

import { ClubFormService } from './club-form.service';

describe('Club Form Service', () => {
  let service: ClubFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClubFormService);
  });

  describe('Service methods', () => {
    describe('createClubFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createClubFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razon: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
            fechaFundacion: expect.any(Object),
            presidente: expect.any(Object),
          }),
        );
      });

      it('passing IClub should create a new form with FormGroup', () => {
        const formGroup = service.createClubFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            razon: expect.any(Object),
            direccion: expect.any(Object),
            telefono: expect.any(Object),
            email: expect.any(Object),
            fechaFundacion: expect.any(Object),
            presidente: expect.any(Object),
          }),
        );
      });
    });

    describe('getClub', () => {
      it('should return NewClub for default Club initial value', () => {
        const formGroup = service.createClubFormGroup(sampleWithNewData);

        const club = service.getClub(formGroup) as any;

        expect(club).toMatchObject(sampleWithNewData);
      });

      it('should return NewClub for empty Club initial value', () => {
        const formGroup = service.createClubFormGroup();

        const club = service.getClub(formGroup) as any;

        expect(club).toMatchObject({});
      });

      it('should return IClub', () => {
        const formGroup = service.createClubFormGroup(sampleWithRequiredData);

        const club = service.getClub(formGroup) as any;

        expect(club).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IClub should not enable id FormControl', () => {
        const formGroup = service.createClubFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewClub should disable id FormControl', () => {
        const formGroup = service.createClubFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
