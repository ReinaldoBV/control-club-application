import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ComunaService } from '../service/comuna.service';
import { IComuna } from '../comuna.model';
import { ComunaFormService } from './comuna-form.service';

import { ComunaUpdateComponent } from './comuna-update.component';

describe('Comuna Management Update Component', () => {
  let comp: ComunaUpdateComponent;
  let fixture: ComponentFixture<ComunaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let comunaFormService: ComunaFormService;
  let comunaService: ComunaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, ComunaUpdateComponent],
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
      .overrideTemplate(ComunaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ComunaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    comunaFormService = TestBed.inject(ComunaFormService);
    comunaService = TestBed.inject(ComunaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const comuna: IComuna = { id: 456 };

      activatedRoute.data = of({ comuna });
      comp.ngOnInit();

      expect(comp.comuna).toEqual(comuna);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComuna>>();
      const comuna = { id: 123 };
      jest.spyOn(comunaFormService, 'getComuna').mockReturnValue(comuna);
      jest.spyOn(comunaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comuna });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comuna }));
      saveSubject.complete();

      // THEN
      expect(comunaFormService.getComuna).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(comunaService.update).toHaveBeenCalledWith(expect.objectContaining(comuna));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComuna>>();
      const comuna = { id: 123 };
      jest.spyOn(comunaFormService, 'getComuna').mockReturnValue({ id: null });
      jest.spyOn(comunaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comuna: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: comuna }));
      saveSubject.complete();

      // THEN
      expect(comunaFormService.getComuna).toHaveBeenCalled();
      expect(comunaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IComuna>>();
      const comuna = { id: 123 };
      jest.spyOn(comunaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ comuna });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(comunaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
