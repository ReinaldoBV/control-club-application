import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../cuerpo-tecnico.test-samples';

import { CuerpoTecnicoFormService } from './cuerpo-tecnico-form.service';

describe('CuerpoTecnico Form Service', () => {
  let service: CuerpoTecnicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CuerpoTecnicoFormService);
  });

  describe('Service methods', () => {
    describe('createCuerpoTecnicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            rolTecnico: expect.any(Object),
            telefono: expect.any(Object),
            fechaInicio: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });

      it('passing ICuerpoTecnico should create a new form with FormGroup', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            rolTecnico: expect.any(Object),
            telefono: expect.any(Object),
            fechaInicio: expect.any(Object),
            email: expect.any(Object),
          }),
        );
      });
    });

    describe('getCuerpoTecnico', () => {
      it('should return NewCuerpoTecnico for default CuerpoTecnico initial value', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup(sampleWithNewData);

        const cuerpoTecnico = service.getCuerpoTecnico(formGroup) as any;

        expect(cuerpoTecnico).toMatchObject(sampleWithNewData);
      });

      it('should return NewCuerpoTecnico for empty CuerpoTecnico initial value', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup();

        const cuerpoTecnico = service.getCuerpoTecnico(formGroup) as any;

        expect(cuerpoTecnico).toMatchObject({});
      });

      it('should return ICuerpoTecnico', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup(sampleWithRequiredData);

        const cuerpoTecnico = service.getCuerpoTecnico(formGroup) as any;

        expect(cuerpoTecnico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICuerpoTecnico should not enable id FormControl', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCuerpoTecnico should disable id FormControl', () => {
        const formGroup = service.createCuerpoTecnicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
