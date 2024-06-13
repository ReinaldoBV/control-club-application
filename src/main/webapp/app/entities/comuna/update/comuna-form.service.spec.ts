import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../comuna.test-samples';

import { ComunaFormService } from './comuna-form.service';

describe('Comuna Form Service', () => {
  let service: ComunaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ComunaFormService);
  });

  describe('Service methods', () => {
    describe('createComunaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createComunaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comuna: expect.any(Object),
          }),
        );
      });

      it('passing IComuna should create a new form with FormGroup', () => {
        const formGroup = service.createComunaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            comuna: expect.any(Object),
          }),
        );
      });
    });

    describe('getComuna', () => {
      it('should return NewComuna for default Comuna initial value', () => {
        const formGroup = service.createComunaFormGroup(sampleWithNewData);

        const comuna = service.getComuna(formGroup) as any;

        expect(comuna).toMatchObject(sampleWithNewData);
      });

      it('should return NewComuna for empty Comuna initial value', () => {
        const formGroup = service.createComunaFormGroup();

        const comuna = service.getComuna(formGroup) as any;

        expect(comuna).toMatchObject({});
      });

      it('should return IComuna', () => {
        const formGroup = service.createComunaFormGroup(sampleWithRequiredData);

        const comuna = service.getComuna(formGroup) as any;

        expect(comuna).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IComuna should not enable id FormControl', () => {
        const formGroup = service.createComunaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewComuna should disable id FormControl', () => {
        const formGroup = service.createComunaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
