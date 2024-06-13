import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { PadreService } from '../service/padre.service';
import { IPadre } from '../padre.model';
import { PadreFormService } from './padre-form.service';

import { PadreUpdateComponent } from './padre-update.component';

describe('Padre Management Update Component', () => {
  let comp: PadreUpdateComponent;
  let fixture: ComponentFixture<PadreUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let padreFormService: PadreFormService;
  let padreService: PadreService;
  let jugadorService: JugadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PadreUpdateComponent],
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
      .overrideTemplate(PadreUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PadreUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    padreFormService = TestBed.inject(PadreFormService);
    padreService = TestBed.inject(PadreService);
    jugadorService = TestBed.inject(JugadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Jugador query and add missing value', () => {
      const padre: IPadre = { id: 456 };
      const jugador: IJugador = { id: 11256 };
      padre.jugador = jugador;

      const jugadorCollection: IJugador[] = [{ id: 157 }];
      jest.spyOn(jugadorService, 'query').mockReturnValue(of(new HttpResponse({ body: jugadorCollection })));
      const additionalJugadors = [jugador];
      const expectedCollection: IJugador[] = [...additionalJugadors, ...jugadorCollection];
      jest.spyOn(jugadorService, 'addJugadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ padre });
      comp.ngOnInit();

      expect(jugadorService.query).toHaveBeenCalled();
      expect(jugadorService.addJugadorToCollectionIfMissing).toHaveBeenCalledWith(
        jugadorCollection,
        ...additionalJugadors.map(expect.objectContaining),
      );
      expect(comp.jugadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const padre: IPadre = { id: 456 };
      const jugador: IJugador = { id: 15065 };
      padre.jugador = jugador;

      activatedRoute.data = of({ padre });
      comp.ngOnInit();

      expect(comp.jugadorsSharedCollection).toContain(jugador);
      expect(comp.padre).toEqual(padre);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPadre>>();
      const padre = { id: 123 };
      jest.spyOn(padreFormService, 'getPadre').mockReturnValue(padre);
      jest.spyOn(padreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ padre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: padre }));
      saveSubject.complete();

      // THEN
      expect(padreFormService.getPadre).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(padreService.update).toHaveBeenCalledWith(expect.objectContaining(padre));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPadre>>();
      const padre = { id: 123 };
      jest.spyOn(padreFormService, 'getPadre').mockReturnValue({ id: null });
      jest.spyOn(padreService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ padre: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: padre }));
      saveSubject.complete();

      // THEN
      expect(padreFormService.getPadre).toHaveBeenCalled();
      expect(padreService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPadre>>();
      const padre = { id: 123 };
      jest.spyOn(padreService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ padre });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(padreService.update).toHaveBeenCalled();
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
