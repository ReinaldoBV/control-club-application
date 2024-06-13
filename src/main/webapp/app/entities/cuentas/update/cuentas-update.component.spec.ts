import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CuentasService } from '../service/cuentas.service';
import { ICuentas } from '../cuentas.model';
import { CuentasFormService } from './cuentas-form.service';

import { CuentasUpdateComponent } from './cuentas-update.component';

describe('Cuentas Management Update Component', () => {
  let comp: CuentasUpdateComponent;
  let fixture: ComponentFixture<CuentasUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cuentasFormService: CuentasFormService;
  let cuentasService: CuentasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CuentasUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CuentasUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CuentasUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cuentasFormService = TestBed.inject(CuentasFormService);
    cuentasService = TestBed.inject(CuentasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cuentas: ICuentas = { id: 456 };

      activatedRoute.data = of({ cuentas });
      comp.ngOnInit();

      expect(comp.cuentas).toEqual(cuentas);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuentas>>();
      const cuentas = { id: 123 };
      jest.spyOn(cuentasFormService, 'getCuentas').mockReturnValue(cuentas);
      jest.spyOn(cuentasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuentas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuentas }));
      saveSubject.complete();

      // THEN
      expect(cuentasFormService.getCuentas).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cuentasService.update).toHaveBeenCalledWith(expect.objectContaining(cuentas));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuentas>>();
      const cuentas = { id: 123 };
      jest.spyOn(cuentasFormService, 'getCuentas').mockReturnValue({ id: null });
      jest.spyOn(cuentasService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuentas: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuentas }));
      saveSubject.complete();

      // THEN
      expect(cuentasFormService.getCuentas).toHaveBeenCalled();
      expect(cuentasService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuentas>>();
      const cuentas = { id: 123 };
      jest.spyOn(cuentasService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuentas });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cuentasService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
