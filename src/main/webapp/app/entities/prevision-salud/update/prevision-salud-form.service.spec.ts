import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../prevision-salud.test-samples';

import { PrevisionSaludFormService } from './prevision-salud-form.service';

describe('PrevisionSalud Form Service', () => {
  let service: PrevisionSaludFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PrevisionSaludFormService);
  });

  describe('Service methods', () => {
    describe('createPrevisionSaludFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPrevisionSaludFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoPrevision: expect.any(Object),
          }),
        );
      });

      it('passing IPrevisionSalud should create a new form with FormGroup', () => {
        const formGroup = service.createPrevisionSaludFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipoPrevision: expect.any(Object),
          }),
        );
      });
    });

    describe('getPrevisionSalud', () => {
      it('should return NewPrevisionSalud for default PrevisionSalud initial value', () => {
        const formGroup = service.createPrevisionSaludFormGroup(sampleWithNewData);

        const previsionSalud = service.getPrevisionSalud(formGroup) as any;

        expect(previsionSalud).toMatchObject(sampleWithNewData);
      });

      it('should return NewPrevisionSalud for empty PrevisionSalud initial value', () => {
        const formGroup = service.createPrevisionSaludFormGroup();

        const previsionSalud = service.getPrevisionSalud(formGroup) as any;

        expect(previsionSalud).toMatchObject({});
      });

      it('should return IPrevisionSalud', () => {
        const formGroup = service.createPrevisionSaludFormGroup(sampleWithRequiredData);

        const previsionSalud = service.getPrevisionSalud(formGroup) as any;

        expect(previsionSalud).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPrevisionSalud should not enable id FormControl', () => {
        const formGroup = service.createPrevisionSaludFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPrevisionSalud should disable id FormControl', () => {
        const formGroup = service.createPrevisionSaludFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
