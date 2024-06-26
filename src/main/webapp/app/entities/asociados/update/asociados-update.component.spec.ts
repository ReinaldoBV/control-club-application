import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AsociadosService } from '../service/asociados.service';
import { IAsociados } from '../asociados.model';
import { AsociadosFormService } from './asociados-form.service';

import { AsociadosUpdateComponent } from './asociados-update.component';

describe('Asociados Management Update Component', () => {
  let comp: AsociadosUpdateComponent;
  let fixture: ComponentFixture<AsociadosUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let asociadosFormService: AsociadosFormService;
  let asociadosService: AsociadosService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, AsociadosUpdateComponent],
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
      .overrideTemplate(AsociadosUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AsociadosUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    asociadosFormService = TestBed.inject(AsociadosFormService);
    asociadosService = TestBed.inject(AsociadosService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const asociados: IAsociados = { id: 456 };

      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      expect(comp.asociados).toEqual(asociados);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosFormService, 'getAsociados').mockReturnValue(asociados);
      jest.spyOn(asociadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asociados }));
      saveSubject.complete();

      // THEN
      expect(asociadosFormService.getAsociados).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(asociadosService.update).toHaveBeenCalledWith(expect.objectContaining(asociados));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosFormService, 'getAsociados').mockReturnValue({ id: null });
      jest.spyOn(asociadosService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: asociados }));
      saveSubject.complete();

      // THEN
      expect(asociadosFormService.getAsociados).toHaveBeenCalled();
      expect(asociadosService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAsociados>>();
      const asociados = { id: 123 };
      jest.spyOn(asociadosService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ asociados });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(asociadosService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
