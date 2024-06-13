import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IComuna } from 'app/entities/comuna/comuna.model';
import { ComunaService } from 'app/entities/comuna/service/comuna.service';
import { CentroSaludService } from '../service/centro-salud.service';
import { ICentroSalud } from '../centro-salud.model';
import { CentroSaludFormService } from './centro-salud-form.service';

import { CentroSaludUpdateComponent } from './centro-salud-update.component';

describe('CentroSalud Management Update Component', () => {
  let comp: CentroSaludUpdateComponent;
  let fixture: ComponentFixture<CentroSaludUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centroSaludFormService: CentroSaludFormService;
  let centroSaludService: CentroSaludService;
  let comunaService: ComunaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CentroSaludUpdateComponent],
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
      .overrideTemplate(CentroSaludUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentroSaludUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centroSaludFormService = TestBed.inject(CentroSaludFormService);
    centroSaludService = TestBed.inject(CentroSaludService);
    comunaService = TestBed.inject(ComunaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Comuna query and add missing value', () => {
      const centroSalud: ICentroSalud = { id: 456 };
      const comuna: IComuna = { id: 29122 };
      centroSalud.comuna = comuna;

      const comunaCollection: IComuna[] = [{ id: 1880 }];
      jest.spyOn(comunaService, 'query').mockReturnValue(of(new HttpResponse({ body: comunaCollection })));
      const additionalComunas = [comuna];
      const expectedCollection: IComuna[] = [...additionalComunas, ...comunaCollection];
      jest.spyOn(comunaService, 'addComunaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      expect(comunaService.query).toHaveBeenCalled();
      expect(comunaService.addComunaToCollectionIfMissing).toHaveBeenCalledWith(
        comunaCollection,
        ...additionalComunas.map(expect.objectContaining),
      );
      expect(comp.comunasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const centroSalud: ICentroSalud = { id: 456 };
      const comuna: IComuna = { id: 11500 };
      centroSalud.comuna = comuna;

      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      expect(comp.comunasSharedCollection).toContain(comuna);
      expect(comp.centroSalud).toEqual(centroSalud);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludFormService, 'getCentroSalud').mockReturnValue(centroSalud);
      jest.spyOn(centroSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroSalud }));
      saveSubject.complete();

      // THEN
      expect(centroSaludFormService.getCentroSalud).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centroSaludService.update).toHaveBeenCalledWith(expect.objectContaining(centroSalud));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludFormService, 'getCentroSalud').mockReturnValue({ id: null });
      jest.spyOn(centroSaludService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroSalud }));
      saveSubject.complete();

      // THEN
      expect(centroSaludFormService.getCentroSalud).toHaveBeenCalled();
      expect(centroSaludService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroSalud>>();
      const centroSalud = { id: 123 };
      jest.spyOn(centroSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centroSaludService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareComuna', () => {
      it('Should forward to comunaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(comunaService, 'compareComuna');
        comp.compareComuna(entity, entity2);
        expect(comunaService.compareComuna).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
