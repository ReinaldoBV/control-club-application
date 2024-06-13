import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const cuerpoTecnico: ICuerpoTecnico = { id: 456 };

      activatedRoute.data = of({ cuerpoTecnico });
      comp.ngOnInit();

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
});
