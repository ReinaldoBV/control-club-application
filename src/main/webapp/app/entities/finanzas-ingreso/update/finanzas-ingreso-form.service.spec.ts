import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../finanzas-ingreso.test-samples';

import { FinanzasIngresoFormService } from './finanzas-ingreso-form.service';

describe('FinanzasIngreso Form Service', () => {
  let service: FinanzasIngresoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FinanzasIngresoFormService);
  });

  describe('Service methods', () => {
    describe('createFinanzasIngresoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFinanzasIngresoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            fecha: expect.any(Object),
          }),
        );
      });

      it('passing IFinanzasIngreso should create a new form with FormGroup', () => {
        const formGroup = service.createFinanzasIngresoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            tipo: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            fecha: expect.any(Object),
          }),
        );
      });
    });

    describe('getFinanzasIngreso', () => {
      it('should return NewFinanzasIngreso for default FinanzasIngreso initial value', () => {
        const formGroup = service.createFinanzasIngresoFormGroup(sampleWithNewData);

        const finanzasIngreso = service.getFinanzasIngreso(formGroup) as any;

        expect(finanzasIngreso).toMatchObject(sampleWithNewData);
      });

      it('should return NewFinanzasIngreso for empty FinanzasIngreso initial value', () => {
        const formGroup = service.createFinanzasIngresoFormGroup();

        const finanzasIngreso = service.getFinanzasIngreso(formGroup) as any;

        expect(finanzasIngreso).toMatchObject({});
      });

      it('should return IFinanzasIngreso', () => {
        const formGroup = service.createFinanzasIngresoFormGroup(sampleWithRequiredData);

        const finanzasIngreso = service.getFinanzasIngreso(formGroup) as any;

        expect(finanzasIngreso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFinanzasIngreso should not enable id FormControl', () => {
        const formGroup = service.createFinanzasIngresoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFinanzasIngreso should disable id FormControl', () => {
        const formGroup = service.createFinanzasIngresoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
