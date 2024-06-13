import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EstadisticasBaloncestoService } from '../service/estadisticas-baloncesto.service';
import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';
import { EstadisticasBaloncestoFormService } from './estadisticas-baloncesto-form.service';

import { EstadisticasBaloncestoUpdateComponent } from './estadisticas-baloncesto-update.component';

describe('EstadisticasBaloncesto Management Update Component', () => {
  let comp: EstadisticasBaloncestoUpdateComponent;
  let fixture: ComponentFixture<EstadisticasBaloncestoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let estadisticasBaloncestoFormService: EstadisticasBaloncestoFormService;
  let estadisticasBaloncestoService: EstadisticasBaloncestoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, EstadisticasBaloncestoUpdateComponent],
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
      .overrideTemplate(EstadisticasBaloncestoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstadisticasBaloncestoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    estadisticasBaloncestoFormService = TestBed.inject(EstadisticasBaloncestoFormService);
    estadisticasBaloncestoService = TestBed.inject(EstadisticasBaloncestoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const estadisticasBaloncesto: IEstadisticasBaloncesto = { id: 456 };

      activatedRoute.data = of({ estadisticasBaloncesto });
      comp.ngOnInit();

      expect(comp.estadisticasBaloncesto).toEqual(estadisticasBaloncesto);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadisticasBaloncesto>>();
      const estadisticasBaloncesto = { id: 123 };
      jest.spyOn(estadisticasBaloncestoFormService, 'getEstadisticasBaloncesto').mockReturnValue(estadisticasBaloncesto);
      jest.spyOn(estadisticasBaloncestoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadisticasBaloncesto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadisticasBaloncesto }));
      saveSubject.complete();

      // THEN
      expect(estadisticasBaloncestoFormService.getEstadisticasBaloncesto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(estadisticasBaloncestoService.update).toHaveBeenCalledWith(expect.objectContaining(estadisticasBaloncesto));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadisticasBaloncesto>>();
      const estadisticasBaloncesto = { id: 123 };
      jest.spyOn(estadisticasBaloncestoFormService, 'getEstadisticasBaloncesto').mockReturnValue({ id: null });
      jest.spyOn(estadisticasBaloncestoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadisticasBaloncesto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: estadisticasBaloncesto }));
      saveSubject.complete();

      // THEN
      expect(estadisticasBaloncestoFormService.getEstadisticasBaloncesto).toHaveBeenCalled();
      expect(estadisticasBaloncestoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEstadisticasBaloncesto>>();
      const estadisticasBaloncesto = { id: 123 };
      jest.spyOn(estadisticasBaloncestoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ estadisticasBaloncesto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(estadisticasBaloncestoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
