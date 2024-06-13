import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../directivos.test-samples';

import { DirectivosFormService } from './directivos-form.service';

describe('Directivos Form Service', () => {
  let service: DirectivosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DirectivosFormService);
  });

  describe('Service methods', () => {
    describe('createDirectivosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDirectivosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            cargo: expect.any(Object),
            telefono: expect.any(Object),
            fechaEleccion: expect.any(Object),
            fechaVencimiento: expect.any(Object),
            email: expect.any(Object),
            asociados: expect.any(Object),
          }),
        );
      });

      it('passing IDirectivos should create a new form with FormGroup', () => {
        const formGroup = service.createDirectivosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            cargo: expect.any(Object),
            telefono: expect.any(Object),
            fechaEleccion: expect.any(Object),
            fechaVencimiento: expect.any(Object),
            email: expect.any(Object),
            asociados: expect.any(Object),
          }),
        );
      });
    });

    describe('getDirectivos', () => {
      it('should return NewDirectivos for default Directivos initial value', () => {
        const formGroup = service.createDirectivosFormGroup(sampleWithNewData);

        const directivos = service.getDirectivos(formGroup) as any;

        expect(directivos).toMatchObject(sampleWithNewData);
      });

      it('should return NewDirectivos for empty Directivos initial value', () => {
        const formGroup = service.createDirectivosFormGroup();

        const directivos = service.getDirectivos(formGroup) as any;

        expect(directivos).toMatchObject({});
      });

      it('should return IDirectivos', () => {
        const formGroup = service.createDirectivosFormGroup(sampleWithRequiredData);

        const directivos = service.getDirectivos(formGroup) as any;

        expect(directivos).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDirectivos should not enable id FormControl', () => {
        const formGroup = service.createDirectivosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDirectivos should disable id FormControl', () => {
        const formGroup = service.createDirectivosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
