import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../centro-educativo.test-samples';

import { CentroEducativoFormService } from './centro-educativo-form.service';

describe('CentroEducativo Form Service', () => {
  let service: CentroEducativoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CentroEducativoFormService);
  });

  describe('Service methods', () => {
    describe('createCentroEducativoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCentroEducativoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centroEducativo: expect.any(Object),
          }),
        );
      });

      it('passing ICentroEducativo should create a new form with FormGroup', () => {
        const formGroup = service.createCentroEducativoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            centroEducativo: expect.any(Object),
          }),
        );
      });
    });

    describe('getCentroEducativo', () => {
      it('should return NewCentroEducativo for default CentroEducativo initial value', () => {
        const formGroup = service.createCentroEducativoFormGroup(sampleWithNewData);

        const centroEducativo = service.getCentroEducativo(formGroup) as any;

        expect(centroEducativo).toMatchObject(sampleWithNewData);
      });

      it('should return NewCentroEducativo for empty CentroEducativo initial value', () => {
        const formGroup = service.createCentroEducativoFormGroup();

        const centroEducativo = service.getCentroEducativo(formGroup) as any;

        expect(centroEducativo).toMatchObject({});
      });

      it('should return ICentroEducativo', () => {
        const formGroup = service.createCentroEducativoFormGroup(sampleWithRequiredData);

        const centroEducativo = service.getCentroEducativo(formGroup) as any;

        expect(centroEducativo).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICentroEducativo should not enable id FormControl', () => {
        const formGroup = service.createCentroEducativoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCentroEducativo should disable id FormControl', () => {
        const formGroup = service.createCentroEducativoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
