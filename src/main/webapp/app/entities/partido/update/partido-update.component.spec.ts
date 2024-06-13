import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PartidoService } from '../service/partido.service';
import { IPartido } from '../partido.model';
import { PartidoFormService } from './partido-form.service';

import { PartidoUpdateComponent } from './partido-update.component';

describe('Partido Management Update Component', () => {
  let comp: PartidoUpdateComponent;
  let fixture: ComponentFixture<PartidoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let partidoFormService: PartidoFormService;
  let partidoService: PartidoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, PartidoUpdateComponent],
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
      .overrideTemplate(PartidoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartidoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    partidoFormService = TestBed.inject(PartidoFormService);
    partidoService = TestBed.inject(PartidoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const partido: IPartido = { id: 456 };

      activatedRoute.data = of({ partido });
      comp.ngOnInit();

      expect(comp.partido).toEqual(partido);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartido>>();
      const partido = { id: 123 };
      jest.spyOn(partidoFormService, 'getPartido').mockReturnValue(partido);
      jest.spyOn(partidoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partido }));
      saveSubject.complete();

      // THEN
      expect(partidoFormService.getPartido).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(partidoService.update).toHaveBeenCalledWith(expect.objectContaining(partido));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartido>>();
      const partido = { id: 123 };
      jest.spyOn(partidoFormService, 'getPartido').mockReturnValue({ id: null });
      jest.spyOn(partidoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partido: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: partido }));
      saveSubject.complete();

      // THEN
      expect(partidoFormService.getPartido).toHaveBeenCalled();
      expect(partidoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPartido>>();
      const partido = { id: 123 };
      jest.spyOn(partidoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ partido });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(partidoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
