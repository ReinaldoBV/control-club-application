import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { DirectivosService } from 'app/entities/directivos/service/directivos.service';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';
import { CuerpoTecnicoService } from 'app/entities/cuerpo-tecnico/service/cuerpo-tecnico.service';
import { IAsociados } from '../asociados.model';
import { AsociadosService } from '../service/asociados.service';
import { AsociadosFormService } from './asociados-form.service';

import { AsociadosUpdateComponent } from './asociados-update.component';

describe('Asociados Management Update Component', () => {
  let comp: AsociadosUpdateComponent;
  let fixture: ComponentFixture<AsociadosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let asociadosFormService: AsociadosFormService;
  let asociadosService: AsociadosService;
  let directivosService: DirectivosService;
  let cuerpoTecnicoService: CuerpoTecnicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AsociadosUpdateComponent],
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
      .overrideTemplate(AsociadosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AsociadosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    asociadosFormService = TestBed.inject(AsociadosFormService);
    asociadosService = TestBed.inject(AsociadosService);
    directivosService = TestBed.inject(DirectivosService);
    cuerpoTecnicoService = TestBed.inject(CuerpoTecnicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call directivos query and add missing value', () => {
      const asociados: IAsociados = { id: 456 };
      const directivos: IDirectivos = { id: 588 };
      asociados.directivos = directivos;

      const directivosCollection: IDirectivos[] = [{ id: 15105 }];
      jest.spyOn(directivosService, 'query').mockReturnValue(of(new HttpResponse({ body: directivosCollection })));
      const expectedCollection: IDirectivos[] = [directivos, ...directivosCollection];
      jest.spyOn(directivosService, 'addDirectivosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      expect(directivosService.query).toHaveBeenCalled();
      expect(directivosService.addDirectivosToCollectionIfMissing).toHaveBeenCalledWith(directivosCollection, directivos);
      expect(comp.directivosCollection).toEqual(expectedCollection);
    });

    it('Should call cuerpoTecnico query and add missing value', () => {
      const asociados: IAsociados = { id: 456 };
      const cuerpoTecnico: ICuerpoTecnico = { id: 26527 };
      asociados.cuerpoTecnico = cuerpoTecnico;

      const cuerpoTecnicoCollection: ICuerpoTecnico[] = [{ id: 142 }];
      jest.spyOn(cuerpoTecnicoService, 'query').mockReturnValue(of(new HttpResponse({ body: cuerpoTecnicoCollection })));
      const expectedCollection: ICuerpoTecnico[] = [cuerpoTecnico, ...cuerpoTecnicoCollection];
      jest.spyOn(cuerpoTecnicoService, 'addCuerpoTecnicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      expect(cuerpoTecnicoService.query).toHaveBeenCalled();
      expect(cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing).toHaveBeenCalledWith(cuerpoTecnicoCollection, cuerpoTecnico);
      expect(comp.cuerpoTecnicosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const asociados: IAsociados = { id: 456 };
      const directivos: IDirectivos = { id: 10024 };
      asociados.directivos = directivos;
      const cuerpoTecnico: ICuerpoTecnico = { id: 9009 };
      asociados.cuerpoTecnico = cuerpoTecnico;

      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      expect(comp.directivosCollection).toContain(directivos);
      expect(comp.cuerpoTecnicosCollection).toContain(cuerpoTecnico);
      expect(comp.asociados).toEqual(asociados);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosFormService, 'getAsociados').mockReturnValue(asociados);
      jest.spyOn(asociadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asociados }));
      saveSubject.complete();

      // THEN
      expect(asociadosFormService.getAsociados).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(asociadosService.update).toHaveBeenCalledWith(expect.objectContaining(asociados));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosFormService, 'getAsociados').mockReturnValue({ id: null });
      jest.spyOn(asociadosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asociados }));
      saveSubject.complete();

      // THEN
      expect(asociadosFormService.getAsociados).toHaveBeenCalled();
      expect(asociadosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(asociadosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareDirectivos', () => {
      it('Should forward to directivosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(directivosService, 'compareDirectivos');
        comp.compareDirectivos(entity, entity2);
        expect(directivosService.compareDirectivos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCuerpoTecnico', () => {
      it('Should forward to cuerpoTecnicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cuerpoTecnicoService, 'compareCuerpoTecnico');
        comp.compareCuerpoTecnico(entity, entity2);
        expect(cuerpoTecnicoService.compareCuerpoTecnico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
