import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { BienesService } from '../service/bienes.service';
import { IBienes } from '../bienes.model';
import { BienesFormService } from './bienes-form.service';

import { BienesUpdateComponent } from './bienes-update.component';

describe('Bienes Management Update Component', () => {
  let comp: BienesUpdateComponent;
  let fixture: ComponentFixture<BienesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let bienesFormService: BienesFormService;
  let bienesService: BienesService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, BienesUpdateComponent],
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
      .overrideTemplate(BienesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BienesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    bienesFormService = TestBed.inject(BienesFormService);
    bienesService = TestBed.inject(BienesService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const bienes: IBienes = { id: 456 };

      activatedRoute.data = of({ bienes });
      comp.ngOnInit();

      expect(comp.bienes).toEqual(bienes);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBienes>>();
      const bienes = { id: 123 };
      jest.spyOn(bienesFormService, 'getBienes').mockReturnValue(bienes);
      jest.spyOn(bienesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bienes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bienes }));
      saveSubject.complete();

      // THEN
      expect(bienesFormService.getBienes).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(bienesService.update).toHaveBeenCalledWith(expect.objectContaining(bienes));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBienes>>();
      const bienes = { id: 123 };
      jest.spyOn(bienesFormService, 'getBienes').mockReturnValue({ id: null });
      jest.spyOn(bienesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bienes: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: bienes }));
      saveSubject.complete();

      // THEN
      expect(bienesFormService.getBienes).toHaveBeenCalled();
      expect(bienesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IBienes>>();
      const bienes = { id: 123 };
      jest.spyOn(bienesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ bienes });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(bienesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
