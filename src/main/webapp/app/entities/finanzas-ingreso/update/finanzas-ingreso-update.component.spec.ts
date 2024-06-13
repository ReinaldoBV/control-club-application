import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { FinanzasIngresoService } from '../service/finanzas-ingreso.service';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';
import { FinanzasIngresoFormService } from './finanzas-ingreso-form.service';

import { FinanzasIngresoUpdateComponent } from './finanzas-ingreso-update.component';

describe('FinanzasIngreso Management Update Component', () => {
  let comp: FinanzasIngresoUpdateComponent;
  let fixture: ComponentFixture<FinanzasIngresoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let finanzasIngresoFormService: FinanzasIngresoFormService;
  let finanzasIngresoService: FinanzasIngresoService;
  let jugadorService: JugadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, FinanzasIngresoUpdateComponent],
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
      .overrideTemplate(FinanzasIngresoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FinanzasIngresoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    finanzasIngresoFormService = TestBed.inject(FinanzasIngresoFormService);
    finanzasIngresoService = TestBed.inject(FinanzasIngresoService);
    jugadorService = TestBed.inject(JugadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Jugador query and add missing value', () => {
      const finanzasIngreso: IFinanzasIngreso = { id: 456 };
      const jugador: IJugador = { id: 16313 };
      finanzasIngreso.jugador = jugador;

      const jugadorCollection: IJugador[] = [{ id: 25575 }];
      jest.spyOn(jugadorService, 'query').mockReturnValue(of(new HttpResponse({ body: jugadorCollection })));
      const additionalJugadors = [jugador];
      const expectedCollection: IJugador[] = [...additionalJugadors, ...jugadorCollection];
      jest.spyOn(jugadorService, 'addJugadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ finanzasIngreso });
      comp.ngOnInit();

      expect(jugadorService.query).toHaveBeenCalled();
      expect(jugadorService.addJugadorToCollectionIfMissing).toHaveBeenCalledWith(
        jugadorCollection,
        ...additionalJugadors.map(expect.objectContaining),
      );
      expect(comp.jugadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const finanzasIngreso: IFinanzasIngreso = { id: 456 };
      const jugador: IJugador = { id: 20130 };
      finanzasIngreso.jugador = jugador;

      activatedRoute.data = of({ finanzasIngreso });
      comp.ngOnInit();

      expect(comp.jugadorsSharedCollection).toContain(jugador);
      expect(comp.finanzasIngreso).toEqual(finanzasIngreso);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasIngreso>>();
      const finanzasIngreso = { id: 123 };
      jest.spyOn(finanzasIngresoFormService, 'getFinanzasIngreso').mockReturnValue(finanzasIngreso);
      jest.spyOn(finanzasIngresoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasIngreso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: finanzasIngreso }));
      saveSubject.complete();

      // THEN
      expect(finanzasIngresoFormService.getFinanzasIngreso).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(finanzasIngresoService.update).toHaveBeenCalledWith(expect.objectContaining(finanzasIngreso));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasIngreso>>();
      const finanzasIngreso = { id: 123 };
      jest.spyOn(finanzasIngresoFormService, 'getFinanzasIngreso').mockReturnValue({ id: null });
      jest.spyOn(finanzasIngresoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasIngreso: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: finanzasIngreso }));
      saveSubject.complete();

      // THEN
      expect(finanzasIngresoFormService.getFinanzasIngreso).toHaveBeenCalled();
      expect(finanzasIngresoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFinanzasIngreso>>();
      const finanzasIngreso = { id: 123 };
      jest.spyOn(finanzasIngresoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ finanzasIngreso });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(finanzasIngresoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareJugador', () => {
      it('Should forward to jugadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(jugadorService, 'compareJugador');
        comp.compareJugador(entity, entity2);
        expect(jugadorService.compareJugador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
