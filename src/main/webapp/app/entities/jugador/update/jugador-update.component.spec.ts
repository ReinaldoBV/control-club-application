import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICategorias } from 'app/entities/categorias/categorias.model';
import { CategoriasService } from 'app/entities/categorias/service/categorias.service';
import { JugadorService } from '../service/jugador.service';
import { IJugador } from '../jugador.model';
import { JugadorFormService } from './jugador-form.service';

import { JugadorUpdateComponent } from './jugador-update.component';

describe('Jugador Management Update Component', () => {
  let comp: JugadorUpdateComponent;
  let fixture: ComponentFixture<JugadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jugadorFormService: JugadorFormService;
  let jugadorService: JugadorService;
  let categoriasService: CategoriasService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, JugadorUpdateComponent],
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
      .overrideTemplate(JugadorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(JugadorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    jugadorFormService = TestBed.inject(JugadorFormService);
    jugadorService = TestBed.inject(JugadorService);
    categoriasService = TestBed.inject(CategoriasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call categorias query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const categorias: ICategorias = { id: 10205 };
      jugador.categorias = categorias;

      const categoriasCollection: ICategorias[] = [{ id: 17129 }];
      jest.spyOn(categoriasService, 'query').mockReturnValue(of(new HttpResponse({ body: categoriasCollection })));
      const expectedCollection: ICategorias[] = [categorias, ...categoriasCollection];
      jest.spyOn(categoriasService, 'addCategoriasToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(categoriasService.query).toHaveBeenCalled();
      expect(categoriasService.addCategoriasToCollectionIfMissing).toHaveBeenCalledWith(categoriasCollection, categorias);
      expect(comp.categoriasCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const jugador: IJugador = { id: 456 };
      const categorias: ICategorias = { id: 28409 };
      jugador.categorias = categorias;

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(comp.categoriasCollection).toContain(categorias);
      expect(comp.jugador).toEqual(jugador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJugador>>();
      const jugador = { id: 123 };
      jest.spyOn(jugadorFormService, 'getJugador').mockReturnValue(jugador);
      jest.spyOn(jugadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jugador }));
      saveSubject.complete();

      // THEN
      expect(jugadorFormService.getJugador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(jugadorService.update).toHaveBeenCalledWith(expect.objectContaining(jugador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJugador>>();
      const jugador = { id: 123 };
      jest.spyOn(jugadorFormService, 'getJugador').mockReturnValue({ id: null });
      jest.spyOn(jugadorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: jugador }));
      saveSubject.complete();

      // THEN
      expect(jugadorFormService.getJugador).toHaveBeenCalled();
      expect(jugadorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IJugador>>();
      const jugador = { id: 123 };
      jest.spyOn(jugadorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(jugadorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCategorias', () => {
      it('Should forward to categoriasService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(categoriasService, 'compareCategorias');
        comp.compareCategorias(entity, entity2);
        expect(categoriasService.compareCategorias).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
