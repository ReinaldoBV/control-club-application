import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../bienes.test-samples';

import { BienesFormService } from './bienes-form.service';

describe('Bienes Form Service', () => {
  let service: BienesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BienesFormService);
  });

  describe('Service methods', () => {
    describe('createBienesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBienesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            cantidad: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });

      it('passing IBienes should create a new form with FormGroup', () => {
        const formGroup = service.createBienesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            cantidad: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });
    });

    describe('getBienes', () => {
      it('should return NewBienes for default Bienes initial value', () => {
        const formGroup = service.createBienesFormGroup(sampleWithNewData);

        const bienes = service.getBienes(formGroup) as any;

        expect(bienes).toMatchObject(sampleWithNewData);
      });

      it('should return NewBienes for empty Bienes initial value', () => {
        const formGroup = service.createBienesFormGroup();

        const bienes = service.getBienes(formGroup) as any;

        expect(bienes).toMatchObject({});
      });

      it('should return IBienes', () => {
        const formGroup = service.createBienesFormGroup(sampleWithRequiredData);

        const bienes = service.getBienes(formGroup) as any;

        expect(bienes).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBienes should not enable id FormControl', () => {
        const formGroup = service.createBienesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBienes should disable id FormControl', () => {
        const formGroup = service.createBienesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
