import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

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

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const directivos: IDirectivos = { id: 456 };

      activatedRoute.data = of({ directivos });
      comp.ngOnInit();

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
});
