import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IAsociados } from 'app/entities/asociados/asociados.model';
import { AsociadosService } from 'app/entities/asociados/service/asociados.service';
import { CuerpoTecnicoService } from '../service/cuerpo-tecnico.service';
import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { CuerpoTecnicoFormService } from './cuerpo-tecnico-form.service';

import { CuerpoTecnicoUpdateComponent } from './cuerpo-tecnico-update.component';

describe('CuerpoTecnico Management Update Component', () => {
  let comp: CuerpoTecnicoUpdateComponent;
  let fixture: ComponentFixture<CuerpoTecnicoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cuerpoTecnicoFormService: CuerpoTecnicoFormService;
  let cuerpoTecnicoService: CuerpoTecnicoService;
  let asociadosService: AsociadosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CuerpoTecnicoUpdateComponent],
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
      .overrideTemplate(CuerpoTecnicoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CuerpoTecnicoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cuerpoTecnicoFormService = TestBed.inject(CuerpoTecnicoFormService);
    cuerpoTecnicoService = TestBed.inject(CuerpoTecnicoService);
    asociadosService = TestBed.inject(AsociadosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call asociados query and add missing value', () => {
      const cuerpoTecnico: ICuerpoTecnico = { id: 456 };
      const asociados: IAsociados = { id: 20629 };
      cuerpoTecnico.asociados = asociados;

      const asociadosCollection: IAsociados[] = [{ id: 19973 }];
      jest.spyOn(asociadosService, 'query').mockReturnValue(of(new HttpResponse({ body: asociadosCollection })));
      const expectedCollection: IAsociados[] = [asociados, ...asociadosCollection];
      jest.spyOn(asociadosService, 'addAsociadosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cuerpoTecnico });
      comp.ngOnInit();

      expect(asociadosService.query).toHaveBeenCalled();
      expect(asociadosService.addAsociadosToCollectionIfMissing).toHaveBeenCalledWith(asociadosCollection, asociados);
      expect(comp.asociadosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cuerpoTecnico: ICuerpoTecnico = { id: 456 };
      const asociados: IAsociados = { id: 21317 };
      cuerpoTecnico.asociados = asociados;

      activatedRoute.data = of({ cuerpoTecnico });
      comp.ngOnInit();

      expect(comp.asociadosCollection).toContain(asociados);
      expect(comp.cuerpoTecnico).toEqual(cuerpoTecnico);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuerpoTecnico>>();
      const cuerpoTecnico = { id: 123 };
      jest.spyOn(cuerpoTecnicoFormService, 'getCuerpoTecnico').mockReturnValue(cuerpoTecnico);
      jest.spyOn(cuerpoTecnicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuerpoTecnico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuerpoTecnico }));
      saveSubject.complete();

      // THEN
      expect(cuerpoTecnicoFormService.getCuerpoTecnico).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(cuerpoTecnicoService.update).toHaveBeenCalledWith(expect.objectContaining(cuerpoTecnico));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuerpoTecnico>>();
      const cuerpoTecnico = { id: 123 };
      jest.spyOn(cuerpoTecnicoFormService, 'getCuerpoTecnico').mockReturnValue({ id: null });
      jest.spyOn(cuerpoTecnicoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuerpoTecnico: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cuerpoTecnico }));
      saveSubject.complete();

      // THEN
      expect(cuerpoTecnicoFormService.getCuerpoTecnico).toHaveBeenCalled();
      expect(cuerpoTecnicoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICuerpoTecnico>>();
      const cuerpoTecnico = { id: 123 };
      jest.spyOn(cuerpoTecnicoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cuerpoTecnico });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cuerpoTecnicoService.update).toHaveBeenCalled();
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
