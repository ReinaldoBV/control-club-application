import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAsociados } from 'app/entities/asociados/asociados.model';
import { AsociadosService } from 'app/entities/asociados/service/asociados.service';
import { DirectivosService } from '../service/directivos.service';
import { IDirectivos } from '../directivos.model';
import { DirectivosFormService } from './directivos-form.service';

import { DirectivosUpdateComponent } from './directivos-update.component';

describe('Directivos Management Update Component', () => {
  let comp: DirectivosUpdateComponent;
  let fixture: ComponentFixture<DirectivosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let directivosFormService: DirectivosFormService;
  let directivosService: DirectivosService;
  let asociadosService: AsociadosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, DirectivosUpdateComponent],
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
      .overrideTemplate(DirectivosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DirectivosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    directivosFormService = TestBed.inject(DirectivosFormService);
    directivosService = TestBed.inject(DirectivosService);
    asociadosService = TestBed.inject(AsociadosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call asociados query and add missing value', () => {
      const directivos: IDirectivos = { id: 456 };
      const asociados: IAsociados = { id: 29850 };
      directivos.asociados = asociados;

      const asociadosCollection: IAsociados[] = [{ id: 24297 }];
      jest.spyOn(asociadosService, 'query').mockReturnValue(of(new HttpResponse({ body: asociadosCollection })));
      const expectedCollection: IAsociados[] = [asociados, ...asociadosCollection];
      jest.spyOn(asociadosService, 'addAsociadosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ directivos });
      comp.ngOnInit();

      expect(asociadosService.query).toHaveBeenCalled();
      expect(asociadosService.addAsociadosToCollectionIfMissing).toHaveBeenCalledWith(asociadosCollection, asociados);
      expect(comp.asociadosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const directivos: IDirectivos = { id: 456 };
      const asociados: IAsociados = { id: 28499 };
      directivos.asociados = asociados;

      activatedRoute.data = of({ directivos });
      comp.ngOnInit();

      expect(comp.asociadosCollection).toContain(asociados);
      expect(comp.directivos).toEqual(directivos);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirectivos>>();
      const directivos = { id: 123 };
      jest.spyOn(directivosFormService, 'getDirectivos').mockReturnValue(directivos);
      jest.spyOn(directivosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ directivos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: directivos }));
      saveSubject.complete();

      // THEN
      expect(directivosFormService.getDirectivos).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(directivosService.update).toHaveBeenCalledWith(expect.objectContaining(directivos));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirectivos>>();
      const directivos = { id: 123 };
      jest.spyOn(directivosFormService, 'getDirectivos').mockReturnValue({ id: null });
      jest.spyOn(directivosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ directivos: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: directivos }));
      saveSubject.complete();

      // THEN
      expect(directivosFormService.getDirectivos).toHaveBeenCalled();
      expect(directivosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDirectivos>>();
      const directivos = { id: 123 };
      jest.spyOn(directivosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ directivos });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(directivosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareAsociados', () => {
      it('Should forward to asociadosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(asociadosService, 'compareAsociados');
        comp.compareAsociados(entity, entity2);
        expect(asociadosService.compareAsociados).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
