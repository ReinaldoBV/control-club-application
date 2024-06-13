import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../categorias.test-samples';

import { CategoriasFormService } from './categorias-form.service';

describe('Categorias Form Service', () => {
  let service: CategoriasFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CategoriasFormService);
  });

  describe('Service methods', () => {
    describe('createCategoriasFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCategoriasFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anioInicio: expect.any(Object),
            anioFinal: expect.any(Object),
            nombreCategoria: expect.any(Object),
          }),
        );
      });

      it('passing ICategorias should create a new form with FormGroup', () => {
        const formGroup = service.createCategoriasFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            anioInicio: expect.any(Object),
            anioFinal: expect.any(Object),
            nombreCategoria: expect.any(Object),
          }),
        );
      });
    });

    describe('getCategorias', () => {
      it('should return NewCategorias for default Categorias initial value', () => {
        const formGroup = service.createCategoriasFormGroup(sampleWithNewData);

        const categorias = service.getCategorias(formGroup) as any;

        expect(categorias).toMatchObject(sampleWithNewData);
      });

      it('should return NewCategorias for empty Categorias initial value', () => {
        const formGroup = service.createCategoriasFormGroup();

        const categorias = service.getCategorias(formGroup) as any;

        expect(categorias).toMatchObject({});
      });

      it('should return ICategorias', () => {
        const formGroup = service.createCategoriasFormGroup(sampleWithRequiredData);

        const categorias = service.getCategorias(formGroup) as any;

        expect(categorias).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICategorias should not enable id FormControl', () => {
        const formGroup = service.createCategoriasFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCategorias should disable id FormControl', () => {
        const formGroup = service.createCategoriasFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
