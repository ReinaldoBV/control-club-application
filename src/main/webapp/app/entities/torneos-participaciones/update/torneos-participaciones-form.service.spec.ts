import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../torneos-participaciones.test-samples';

import { TorneosParticipacionesFormService } from './torneos-participaciones-form.service';

describe('TorneosParticipaciones Form Service', () => {
  let service: TorneosParticipacionesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TorneosParticipacionesFormService);
  });

  describe('Service methods', () => {
    describe('createTorneosParticipacionesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            fecha: expect.any(Object),
          }),
        );
      });

      it('passing ITorneosParticipaciones should create a new form with FormGroup', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            descripcion: expect.any(Object),
            monto: expect.any(Object),
            fecha: expect.any(Object),
          }),
        );
      });
    });

    describe('getTorneosParticipaciones', () => {
      it('should return NewTorneosParticipaciones for default TorneosParticipaciones initial value', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup(sampleWithNewData);

        const torneosParticipaciones = service.getTorneosParticipaciones(formGroup) as any;

        expect(torneosParticipaciones).toMatchObject(sampleWithNewData);
      });

      it('should return NewTorneosParticipaciones for empty TorneosParticipaciones initial value', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup();

        const torneosParticipaciones = service.getTorneosParticipaciones(formGroup) as any;

        expect(torneosParticipaciones).toMatchObject({});
      });

      it('should return ITorneosParticipaciones', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup(sampleWithRequiredData);

        const torneosParticipaciones = service.getTorneosParticipaciones(formGroup) as any;

        expect(torneosParticipaciones).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITorneosParticipaciones should not enable id FormControl', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTorneosParticipaciones should disable id FormControl', () => {
        const formGroup = service.createTorneosParticipacionesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
