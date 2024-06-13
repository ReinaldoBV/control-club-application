import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { CentroEducativoService } from '../service/centro-educativo.service';
import { ICentroEducativo } from '../centro-educativo.model';
import { CentroEducativoFormService } from './centro-educativo-form.service';

import { CentroEducativoUpdateComponent } from './centro-educativo-update.component';

describe('CentroEducativo Management Update Component', () => {
  let comp: CentroEducativoUpdateComponent;
  let fixture: ComponentFixture<CentroEducativoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let centroEducativoFormService: CentroEducativoFormService;
  let centroEducativoService: CentroEducativoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, CentroEducativoUpdateComponent],
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
      .overrideTemplate(CentroEducativoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CentroEducativoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    centroEducativoFormService = TestBed.inject(CentroEducativoFormService);
    centroEducativoService = TestBed.inject(CentroEducativoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const centroEducativo: ICentroEducativo = { id: 456 };

      activatedRoute.data = of({ centroEducativo });
      comp.ngOnInit();

      expect(comp.centroEducativo).toEqual(centroEducativo);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroEducativo>>();
      const centroEducativo = { id: 123 };
      jest.spyOn(centroEducativoFormService, 'getCentroEducativo').mockReturnValue(centroEducativo);
      jest.spyOn(centroEducativoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroEducativo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroEducativo }));
      saveSubject.complete();

      // THEN
      expect(centroEducativoFormService.getCentroEducativo).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(centroEducativoService.update).toHaveBeenCalledWith(expect.objectContaining(centroEducativo));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroEducativo>>();
      const centroEducativo = { id: 123 };
      jest.spyOn(centroEducativoFormService, 'getCentroEducativo').mockReturnValue({ id: null });
      jest.spyOn(centroEducativoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroEducativo: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: centroEducativo }));
      saveSubject.complete();

      // THEN
      expect(centroEducativoFormService.getCentroEducativo).toHaveBeenCalled();
      expect(centroEducativoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICentroEducativo>>();
      const centroEducativo = { id: 123 };
      jest.spyOn(centroEducativoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ centroEducativo });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(centroEducativoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
