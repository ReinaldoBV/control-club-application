import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IPrevisionSalud } from 'app/entities/prevision-salud/prevision-salud.model';
import { PrevisionSaludService } from 'app/entities/prevision-salud/service/prevision-salud.service';
import { IComuna } from 'app/entities/comuna/comuna.model';
import { ComunaService } from 'app/entities/comuna/service/comuna.service';
import { ICentroEducativo } from 'app/entities/centro-educativo/centro-educativo.model';
import { CentroEducativoService } from 'app/entities/centro-educativo/service/centro-educativo.service';
import { ICategorias } from 'app/entities/categorias/categorias.model';
import { CategoriasService } from 'app/entities/categorias/service/categorias.service';
import { IJugador } from '../jugador.model';
import { JugadorService } from '../service/jugador.service';
import { JugadorFormService } from './jugador-form.service';

import { JugadorUpdateComponent } from './jugador-update.component';

describe('Jugador Management Update Component', () => {
  let comp: JugadorUpdateComponent;
  let fixture: ComponentFixture<JugadorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let jugadorFormService: JugadorFormService;
  let jugadorService: JugadorService;
  let centroSaludService: CentroSaludService;
  let previsionSaludService: PrevisionSaludService;
  let comunaService: ComunaService;
  let centroEducativoService: CentroEducativoService;
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
    centroSaludService = TestBed.inject(CentroSaludService);
    previsionSaludService = TestBed.inject(PrevisionSaludService);
    comunaService = TestBed.inject(ComunaService);
    centroEducativoService = TestBed.inject(CentroEducativoService);
    categoriasService = TestBed.inject(CategoriasService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call centroSalud query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const centroSalud: ICentroSalud = { id: 11456 };
      jugador.centroSalud = centroSalud;

      const centroSaludCollection: ICentroSalud[] = [{ id: 17024 }];
      jest.spyOn(centroSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: centroSaludCollection })));
      const expectedCollection: ICentroSalud[] = [centroSalud, ...centroSaludCollection];
      jest.spyOn(centroSaludService, 'addCentroSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(centroSaludService.query).toHaveBeenCalled();
      expect(centroSaludService.addCentroSaludToCollectionIfMissing).toHaveBeenCalledWith(centroSaludCollection, centroSalud);
      expect(comp.centroSaludsCollection).toEqual(expectedCollection);
    });

    it('Should call previsionSalud query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const previsionSalud: IPrevisionSalud = { id: 6399 };
      jugador.previsionSalud = previsionSalud;

      const previsionSaludCollection: IPrevisionSalud[] = [{ id: 30763 }];
      jest.spyOn(previsionSaludService, 'query').mockReturnValue(of(new HttpResponse({ body: previsionSaludCollection })));
      const expectedCollection: IPrevisionSalud[] = [previsionSalud, ...previsionSaludCollection];
      jest.spyOn(previsionSaludService, 'addPrevisionSaludToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(previsionSaludService.query).toHaveBeenCalled();
      expect(previsionSaludService.addPrevisionSaludToCollectionIfMissing).toHaveBeenCalledWith(previsionSaludCollection, previsionSalud);
      expect(comp.previsionSaludsCollection).toEqual(expectedCollection);
    });

    it('Should call comuna query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const comuna: IComuna = { id: 29122 };
      jugador.comuna = comuna;

      const comunaCollection: IComuna[] = [{ id: 1880 }];
      jest.spyOn(comunaService, 'query').mockReturnValue(of(new HttpResponse({ body: comunaCollection })));
      const expectedCollection: IComuna[] = [comuna, ...comunaCollection];
      jest.spyOn(comunaService, 'addComunaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(comunaService.query).toHaveBeenCalled();
      expect(comunaService.addComunaToCollectionIfMissing).toHaveBeenCalledWith(comunaCollection, comuna);
      expect(comp.comunasCollection).toEqual(expectedCollection);
    });

    it('Should call centroEducativo query and add missing value', () => {
      const jugador: IJugador = { id: 456 };
      const centroEducativo: ICentroEducativo = { id: 31971 };
      jugador.centroEducativo = centroEducativo;

      const centroEducativoCollection: ICentroEducativo[] = [{ id: 26747 }];
      jest.spyOn(centroEducativoService, 'query').mockReturnValue(of(new HttpResponse({ body: centroEducativoCollection })));
      const expectedCollection: ICentroEducativo[] = [centroEducativo, ...centroEducativoCollection];
      jest.spyOn(centroEducativoService, 'addCentroEducativoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(centroEducativoService.query).toHaveBeenCalled();
      expect(centroEducativoService.addCentroEducativoToCollectionIfMissing).toHaveBeenCalledWith(
        centroEducativoCollection,
        centroEducativo,
      );
      expect(comp.centroEducativosCollection).toEqual(expectedCollection);
    });

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
      const centroSalud: ICentroSalud = { id: 32530 };
      jugador.centroSalud = centroSalud;
      const previsionSalud: IPrevisionSalud = { id: 29485 };
      jugador.previsionSalud = previsionSalud;
      const comuna: IComuna = { id: 11500 };
      jugador.comuna = comuna;
      const centroEducativo: ICentroEducativo = { id: 26567 };
      jugador.centroEducativo = centroEducativo;
      const categorias: ICategorias = { id: 28409 };
      jugador.categorias = categorias;

      activatedRoute.data = of({ jugador });
      comp.ngOnInit();

      expect(comp.centroSaludsCollection).toContain(centroSalud);
      expect(comp.previsionSaludsCollection).toContain(previsionSalud);
      expect(comp.comunasCollection).toContain(comuna);
      expect(comp.centroEducativosCollection).toContain(centroEducativo);
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
    describe('compareCentroSalud', () => {
      it('Should forward to centroSaludService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroSaludService, 'compareCentroSalud');
        comp.compareCentroSalud(entity, entity2);
        expect(centroSaludService.compareCentroSalud).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePrevisionSalud', () => {
      it('Should forward to previsionSaludService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(previsionSaludService, 'comparePrevisionSalud');
        comp.comparePrevisionSalud(entity, entity2);
        expect(previsionSaludService.comparePrevisionSalud).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareComuna', () => {
      it('Should forward to comunaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(comunaService, 'compareComuna');
        comp.compareComuna(entity, entity2);
        expect(comunaService.compareComuna).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCentroEducativo', () => {
      it('Should forward to centroEducativoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(centroEducativoService, 'compareCentroEducativo');
        comp.compareCentroEducativo(entity, entity2);
        expect(centroEducativoService.compareCentroEducativo).toHaveBeenCalledWith(entity, entity2);
      });
    });

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
