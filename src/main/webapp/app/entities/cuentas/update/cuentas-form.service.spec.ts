import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cuentas.test-samples';

import { CuentasFormService } from './cuentas-form.service';

describe('Cuentas Form Service', () => {
  let service: CuentasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuentasFormService);
  });

  describe('Service methods', () => {
    describe('createCuentasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCuentasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            nroCuotas: expect.any(Object),
            fechaVencimiento: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });

      it('passing ICuentas should create a new form with FormGroup', () => {
        const formGroup = service.createCuentasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            nroCuotas: expect.any(Object),
            fechaVencimiento: expect.any(Object),
            estado: expect.any(Object),
          }),
        );
      });
    });

    describe('getCuentas', () => {
      it('should return NewCuentas for default Cuentas initial value', () => {
        const formGroup = service.createCuentasFormGroup(sampleWithNewData);

        const cuentas = service.getCuentas(formGroup) as any;

        expect(cuentas).toMatchObject(sampleWithNewData);
      });

      it('should return NewCuentas for empty Cuentas initial value', () => {
        const formGroup = service.createCuentasFormGroup();

        const cuentas = service.getCuentas(formGroup) as any;

        expect(cuentas).toMatchObject({});
      });

      it('should return ICuentas', () => {
        const formGroup = service.createCuentasFormGroup(sampleWithRequiredData);

        const cuentas = service.getCuentas(formGroup) as any;

        expect(cuentas).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICuentas should not enable id FormControl', () => {
        const formGroup = service.createCuentasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCuentas should disable id FormControl', () => {
        const formGroup = service.createCuentasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
