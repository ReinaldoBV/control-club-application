import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICuentas } from 'app/entities/cuentas/cuentas.model';
import { CuentasService } from 'app/entities/cuentas/service/cuentas.service';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';
import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { FinanzasEgresoFormService } from './finanzas-egreso-form.service';

import { FinanzasEgresoUpdateComponent } from './finanzas-egreso-update.component';

describe('FinanzasEgreso Management Update Component', () => {
  let comp: FinanzasEgresoUpdateComponent;
  let fixture: ComponentFixture<FinanzasEgresoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let finanzasEgresoFormService: FinanzasEgresoFormService;
  let finanzasEgresoService: FinanzasEgresoService;
  let cuentasService: CuentasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FinanzasEgresoUpdateComponent],
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
      .overrideTemplate(FinanzasEgresoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FinanzasEgresoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    finanzasEgresoFormService = TestBed.inject(FinanzasEgresoFormService);
    finanzasEgresoService = TestBed.inject(FinanzasEgresoService);
    cuentasService = TestBed.inject(CuentasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cuentas query and add missing value', () => {
      const finanzasEgreso: IFinanzasEgreso = { id: 456 };
      const cuentas: ICuentas = { id: 22966 };
      finanzasEgreso.cuentas = cuentas;

      const cuentasCollection: ICuentas[] = [{ id: 22846 }];
      jest.spyOn(cuentasService, 'query').mockReturnValue(of(new HttpResponse({ body: cuentasCollection })));
      const additionalCuentas = [cuentas];
      const expectedCollection: ICuentas[] = [...additionalCuentas, ...cuentasCollection];
      jest.spyOn(cuentasService, 'addCuentasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ finanzasEgreso });
      comp.ngOnInit();

      expect(cuentasService.query).toHaveBeenCalled();
      expect(cuentasService.addCuentasToCollectionIfMissing).toHaveBeenCalledWith(
        cuentasCollection,
        ...additionalCuentas.map(expect.objectContaining),
      );
      expect(comp.cuentasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const finanzasEgreso: IFinanzasEgreso = { id: 456 };
      const cuentas: ICuentas = { id: 2740 };
      finanzasEgreso.cuentas = cuentas;

      activatedRoute.data = of({ finanzasEgreso });
      comp.ngOnInit();

      expect(comp.cuentasSharedCollection).toContain(cuentas);
      expect(comp.finanzasEgreso).toEqual(finanzasEgreso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasEgreso>>();
      const finanzasEgreso = { id: 123 };
      jest.spyOn(finanzasEgresoFormService, 'getFinanzasEgreso').mockReturnValue(finanzasEgreso);
      jest.spyOn(finanzasEgresoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasEgreso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: finanzasEgreso }));
      saveSubject.complete();

      // THEN
      expect(finanzasEgresoFormService.getFinanzasEgreso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(finanzasEgresoService.update).toHaveBeenCalledWith(expect.objectContaining(finanzasEgreso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasEgreso>>();
      const finanzasEgreso = { id: 123 };
      jest.spyOn(finanzasEgresoFormService, 'getFinanzasEgreso').mockReturnValue({ id: null });
      jest.spyOn(finanzasEgresoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasEgreso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: finanzasEgreso }));
      saveSubject.complete();

      // THEN
      expect(finanzasEgresoFormService.getFinanzasEgreso).toHaveBeenCalled();
      expect(finanzasEgresoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasEgreso>>();
      const finanzasEgreso = { id: 123 };
      jest.spyOn(finanzasEgresoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasEgreso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(finanzasEgresoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCuentas', () => {
      it('Should forward to cuentasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cuentasService, 'compareCuentas');
        comp.compareCuentas(entity, entity2);
        expect(cuentasService.compareCuentas).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
