import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { TorneosParticipacionesService } from '../service/torneos-participaciones.service';
import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import { TorneosParticipacionesFormService } from './torneos-participaciones-form.service';

import { TorneosParticipacionesUpdateComponent } from './torneos-participaciones-update.component';

describe('TorneosParticipaciones Management Update Component', () => {
  let comp: TorneosParticipacionesUpdateComponent;
  let fixture: ComponentFixture<TorneosParticipacionesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let torneosParticipacionesFormService: TorneosParticipacionesFormService;
  let torneosParticipacionesService: TorneosParticipacionesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, TorneosParticipacionesUpdateComponent],
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
      .overrideTemplate(TorneosParticipacionesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TorneosParticipacionesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    torneosParticipacionesFormService = TestBed.inject(TorneosParticipacionesFormService);
    torneosParticipacionesService = TestBed.inject(TorneosParticipacionesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const torneosParticipaciones: ITorneosParticipaciones = { id: 456 };

      activatedRoute.data = of({ torneosParticipaciones });
      comp.ngOnInit();

      expect(comp.torneosParticipaciones).toEqual(torneosParticipaciones);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneosParticipaciones>>();
      const torneosParticipaciones = { id: 123 };
      jest.spyOn(torneosParticipacionesFormService, 'getTorneosParticipaciones').mockReturnValue(torneosParticipaciones);
      jest.spyOn(torneosParticipacionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneosParticipaciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: torneosParticipaciones }));
      saveSubject.complete();

      // THEN
      expect(torneosParticipacionesFormService.getTorneosParticipaciones).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(torneosParticipacionesService.update).toHaveBeenCalledWith(expect.objectContaining(torneosParticipaciones));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneosParticipaciones>>();
      const torneosParticipaciones = { id: 123 };
      jest.spyOn(torneosParticipacionesFormService, 'getTorneosParticipaciones').mockReturnValue({ id: null });
      jest.spyOn(torneosParticipacionesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneosParticipaciones: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: torneosParticipaciones }));
      saveSubject.complete();

      // THEN
      expect(torneosParticipacionesFormService.getTorneosParticipaciones).toHaveBeenCalled();
      expect(torneosParticipacionesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITorneosParticipaciones>>();
      const torneosParticipaciones = { id: 123 };
      jest.spyOn(torneosParticipacionesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ torneosParticipaciones });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(torneosParticipacionesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
