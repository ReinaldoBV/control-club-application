import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PrevisionSaludService } from '../service/prevision-salud.service';
import { IPrevisionSalud } from '../prevision-salud.model';
import { PrevisionSaludFormService } from './prevision-salud-form.service';

import { PrevisionSaludUpdateComponent } from './prevision-salud-update.component';

describe('PrevisionSalud Management Update Component', () => {
  let comp: PrevisionSaludUpdateComponent;
  let fixture: ComponentFixture<PrevisionSaludUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let previsionSaludFormService: PrevisionSaludFormService;
  let previsionSaludService: PrevisionSaludService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PrevisionSaludUpdateComponent],
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
      .overrideTemplate(PrevisionSaludUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PrevisionSaludUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    previsionSaludFormService = TestBed.inject(PrevisionSaludFormService);
    previsionSaludService = TestBed.inject(PrevisionSaludService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const previsionSalud: IPrevisionSalud = { id: 456 };

      activatedRoute.data = of({ previsionSalud });
      comp.ngOnInit();

      expect(comp.previsionSalud).toEqual(previsionSalud);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrevisionSalud>>();
      const previsionSalud = { id: 123 };
      jest.spyOn(previsionSaludFormService, 'getPrevisionSalud').mockReturnValue(previsionSalud);
      jest.spyOn(previsionSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ previsionSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: previsionSalud }));
      saveSubject.complete();

      // THEN
      expect(previsionSaludFormService.getPrevisionSalud).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(previsionSaludService.update).toHaveBeenCalledWith(expect.objectContaining(previsionSalud));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrevisionSalud>>();
      const previsionSalud = { id: 123 };
      jest.spyOn(previsionSaludFormService, 'getPrevisionSalud').mockReturnValue({ id: null });
      jest.spyOn(previsionSaludService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ previsionSalud: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: previsionSalud }));
      saveSubject.complete();

      // THEN
      expect(previsionSaludFormService.getPrevisionSalud).toHaveBeenCalled();
      expect(previsionSaludService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPrevisionSalud>>();
      const previsionSalud = { id: 123 };
      jest.spyOn(previsionSaludService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ previsionSalud });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(previsionSaludService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
