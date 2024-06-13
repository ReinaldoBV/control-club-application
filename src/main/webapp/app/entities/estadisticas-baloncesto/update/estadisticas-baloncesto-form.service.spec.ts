import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../estadisticas-baloncesto.test-samples';

import { EstadisticasBaloncestoFormService } from './estadisticas-baloncesto-form.service';

describe('EstadisticasBaloncesto Form Service', () => {
  let service: EstadisticasBaloncestoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstadisticasBaloncestoFormService);
  });

  describe('Service methods', () => {
    describe('createEstadisticasBaloncestoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntos: expect.any(Object),
            rebotes: expect.any(Object),
            asistencias: expect.any(Object),
            robos: expect.any(Object),
            bloqueos: expect.any(Object),
            porcentajeTiro: expect.any(Object),
          }),
        );
      });

      it('passing IEstadisticasBaloncesto should create a new form with FormGroup', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            puntos: expect.any(Object),
            rebotes: expect.any(Object),
            asistencias: expect.any(Object),
            robos: expect.any(Object),
            bloqueos: expect.any(Object),
            porcentajeTiro: expect.any(Object),
          }),
        );
      });
    });

    describe('getEstadisticasBaloncesto', () => {
      it('should return NewEstadisticasBaloncesto for default EstadisticasBaloncesto initial value', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup(sampleWithNewData);

        const estadisticasBaloncesto = service.getEstadisticasBaloncesto(formGroup) as any;

        expect(estadisticasBaloncesto).toMatchObject(sampleWithNewData);
      });

      it('should return NewEstadisticasBaloncesto for empty EstadisticasBaloncesto initial value', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup();

        const estadisticasBaloncesto = service.getEstadisticasBaloncesto(formGroup) as any;

        expect(estadisticasBaloncesto).toMatchObject({});
      });

      it('should return IEstadisticasBaloncesto', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup(sampleWithRequiredData);

        const estadisticasBaloncesto = service.getEstadisticasBaloncesto(formGroup) as any;

        expect(estadisticasBaloncesto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEstadisticasBaloncesto should not enable id FormControl', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEstadisticasBaloncesto should disable id FormControl', () => {
        const formGroup = service.createEstadisticasBaloncestoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
