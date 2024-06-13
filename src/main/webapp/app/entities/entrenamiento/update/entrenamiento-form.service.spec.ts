import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../entrenamiento.test-samples';

import { EntrenamientoFormService } from './entrenamiento-form.service';

describe('Entrenamiento Form Service', () => {
  let service: EntrenamientoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EntrenamientoFormService);
  });

  describe('Service methods', () => {
    describe('createEntrenamientoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEntrenamientoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaHora: expect.any(Object),
            duracion: expect.any(Object),
          }),
        );
      });

      it('passing IEntrenamiento should create a new form with FormGroup', () => {
        const formGroup = service.createEntrenamientoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fechaHora: expect.any(Object),
            duracion: expect.any(Object),
          }),
        );
      });
    });

    describe('getEntrenamiento', () => {
      it('should return NewEntrenamiento for default Entrenamiento initial value', () => {
        const formGroup = service.createEntrenamientoFormGroup(sampleWithNewData);

        const entrenamiento = service.getEntrenamiento(formGroup) as any;

        expect(entrenamiento).toMatchObject(sampleWithNewData);
      });

      it('should return NewEntrenamiento for empty Entrenamiento initial value', () => {
        const formGroup = service.createEntrenamientoFormGroup();

        const entrenamiento = service.getEntrenamiento(formGroup) as any;

        expect(entrenamiento).toMatchObject({});
      });

      it('should return IEntrenamiento', () => {
        const formGroup = service.createEntrenamientoFormGroup(sampleWithRequiredData);

        const entrenamiento = service.getEntrenamiento(formGroup) as any;

        expect(entrenamiento).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEntrenamiento should not enable id FormControl', () => {
        const formGroup = service.createEntrenamientoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEntrenamiento should disable id FormControl', () => {
        const formGroup = service.createEntrenamientoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
