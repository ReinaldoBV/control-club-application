import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../asociados.test-samples';

import { AsociadosFormService } from './asociados-form.service';

describe('Asociados Form Service', () => {
  let service: AsociadosFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AsociadosFormService);
  });

  describe('Service methods', () => {
    describe('createAsociadosFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAsociadosFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            cargo: expect.any(Object),
            telefono: expect.any(Object),
            fechaAsoc: expect.any(Object),
            email: expect.any(Object),
            jugador: expect.any(Object),
          }),
        );
      });

      it('passing IAsociados should create a new form with FormGroup', () => {
        const formGroup = service.createAsociadosFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            cargo: expect.any(Object),
            telefono: expect.any(Object),
            fechaAsoc: expect.any(Object),
            email: expect.any(Object),
            jugador: expect.any(Object),
          }),
        );
      });
    });

    describe('getAsociados', () => {
      it('should return NewAsociados for default Asociados initial value', () => {
        const formGroup = service.createAsociadosFormGroup(sampleWithNewData);

        const asociados = service.getAsociados(formGroup) as any;

        expect(asociados).toMatchObject(sampleWithNewData);
      });

      it('should return NewAsociados for empty Asociados initial value', () => {
        const formGroup = service.createAsociadosFormGroup();

        const asociados = service.getAsociados(formGroup) as any;

        expect(asociados).toMatchObject({});
      });

      it('should return IAsociados', () => {
        const formGroup = service.createAsociadosFormGroup(sampleWithRequiredData);

        const asociados = service.getAsociados(formGroup) as any;

        expect(asociados).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAsociados should not enable id FormControl', () => {
        const formGroup = service.createAsociadosFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAsociados should disable id FormControl', () => {
        const formGroup = service.createAsociadosFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
