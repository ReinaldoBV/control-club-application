import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../partido.test-samples';

import { PartidoFormService } from './partido-form.service';

describe('Partido Form Service', () => {
  let service: PartidoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PartidoFormService);
  });

  describe('Service methods', () => {
    describe('createPartidoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPartidoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            oponente: expect.any(Object),
            resultado: expect.any(Object),
          }),
        );
      });

      it('passing IPartido should create a new form with FormGroup', () => {
        const formGroup = service.createPartidoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fecha: expect.any(Object),
            oponente: expect.any(Object),
            resultado: expect.any(Object),
          }),
        );
      });
    });

    describe('getPartido', () => {
      it('should return NewPartido for default Partido initial value', () => {
        const formGroup = service.createPartidoFormGroup(sampleWithNewData);

        const partido = service.getPartido(formGroup) as any;

        expect(partido).toMatchObject(sampleWithNewData);
      });

      it('should return NewPartido for empty Partido initial value', () => {
        const formGroup = service.createPartidoFormGroup();

        const partido = service.getPartido(formGroup) as any;

        expect(partido).toMatchObject({});
      });

      it('should return IPartido', () => {
        const formGroup = service.createPartidoFormGroup(sampleWithRequiredData);

        const partido = service.getPartido(formGroup) as any;

        expect(partido).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPartido should not enable id FormControl', () => {
        const formGroup = service.createPartidoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPartido should disable id FormControl', () => {
        const formGroup = service.createPartidoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
