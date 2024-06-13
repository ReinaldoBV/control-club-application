jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EstadisticasBaloncestoService } from '../service/estadisticas-baloncesto.service';

import { EstadisticasBaloncestoDeleteDialogComponent } from './estadisticas-baloncesto-delete-dialog.component';

describe('EstadisticasBaloncesto Management Delete Component', () => {
  let comp: EstadisticasBaloncestoDeleteDialogComponent;
  let fixture: ComponentFixture<EstadisticasBaloncestoDeleteDialogComponent>;
  let service: EstadisticasBaloncestoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, EstadisticasBaloncestoDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EstadisticasBaloncestoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstadisticasBaloncestoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EstadisticasBaloncestoService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
