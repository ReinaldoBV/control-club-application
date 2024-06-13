import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../finanzas-egreso.test-samples';

import { FinanzasEgresoFormService } from './finanzas-egreso-form.service';

describe('FinanzasEgreso Form Service', () => {
  let service: FinanzasEgresoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FinanzasEgresoFormService);
  });

  describe('Service methods', () => {
    describe('createFinanzasEgresoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFinanzasEgresoFormGroup();

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

      it('passing IFinanzasEgreso should create a new form with FormGroup', () => {
        const formGroup = service.createFinanzasEgresoFormGroup(sampleWithRequiredData);

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

    describe('getFinanzasEgreso', () => {
      it('should return NewFinanzasEgreso for default FinanzasEgreso initial value', () => {
        const formGroup = service.createFinanzasEgresoFormGroup(sampleWithNewData);

        const finanzasEgreso = service.getFinanzasEgreso(formGroup) as any;

        expect(finanzasEgreso).toMatchObject(sampleWithNewData);
      });

      it('should return NewFinanzasEgreso for empty FinanzasEgreso initial value', () => {
        const formGroup = service.createFinanzasEgresoFormGroup();

        const finanzasEgreso = service.getFinanzasEgreso(formGroup) as any;

        expect(finanzasEgreso).toMatchObject({});
      });

      it('should return IFinanzasEgreso', () => {
        const formGroup = service.createFinanzasEgresoFormGroup(sampleWithRequiredData);

        const finanzasEgreso = service.getFinanzasEgreso(formGroup) as any;

        expect(finanzasEgreso).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFinanzasEgreso should not enable id FormControl', () => {
        const formGroup = service.createFinanzasEgresoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFinanzasEgreso should disable id FormControl', () => {
        const formGroup = service.createFinanzasEgresoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
