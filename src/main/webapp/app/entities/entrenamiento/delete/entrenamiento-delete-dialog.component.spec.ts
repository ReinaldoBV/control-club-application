jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EntrenamientoService } from '../service/entrenamiento.service';

import { EntrenamientoDeleteDialogComponent } from './entrenamiento-delete-dialog.component';

describe('Entrenamiento Management Delete Component', () => {
  let comp: EntrenamientoDeleteDialogComponent;
  let fixture: ComponentFixture<EntrenamientoDeleteDialogComponent>;
  let service: EntrenamientoService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, EntrenamientoDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EntrenamientoDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EntrenamientoDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EntrenamientoService);
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
