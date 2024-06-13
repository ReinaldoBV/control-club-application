import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EntrenamientoService } from '../service/entrenamiento.service';
import { IEntrenamiento } from '../entrenamiento.model';
import { EntrenamientoFormService } from './entrenamiento-form.service';

import { EntrenamientoUpdateComponent } from './entrenamiento-update.component';

describe('Entrenamiento Management Update Component', () => {
  let comp: EntrenamientoUpdateComponent;
  let fixture: ComponentFixture<EntrenamientoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let entrenamientoFormService: EntrenamientoFormService;
  let entrenamientoService: EntrenamientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, EntrenamientoUpdateComponent],
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
      .overrideTemplate(EntrenamientoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EntrenamientoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    entrenamientoFormService = TestBed.inject(EntrenamientoFormService);
    entrenamientoService = TestBed.inject(EntrenamientoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const entrenamiento: IEntrenamiento = { id: 456 };

      activatedRoute.data = of({ entrenamiento });
      comp.ngOnInit();

      expect(comp.entrenamiento).toEqual(entrenamiento);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrenamiento>>();
      const entrenamiento = { id: 123 };
      jest.spyOn(entrenamientoFormService, 'getEntrenamiento').mockReturnValue(entrenamiento);
      jest.spyOn(entrenamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrenamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entrenamiento }));
      saveSubject.complete();

      // THEN
      expect(entrenamientoFormService.getEntrenamiento).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(entrenamientoService.update).toHaveBeenCalledWith(expect.objectContaining(entrenamiento));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrenamiento>>();
      const entrenamiento = { id: 123 };
      jest.spyOn(entrenamientoFormService, 'getEntrenamiento').mockReturnValue({ id: null });
      jest.spyOn(entrenamientoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrenamiento: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: entrenamiento }));
      saveSubject.complete();

      // THEN
      expect(entrenamientoFormService.getEntrenamiento).toHaveBeenCalled();
      expect(entrenamientoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEntrenamiento>>();
      const entrenamiento = { id: 123 };
      jest.spyOn(entrenamientoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ entrenamiento });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(entrenamientoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
