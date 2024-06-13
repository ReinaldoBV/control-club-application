import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { MensajeService } from '../service/mensaje.service';
import { IMensaje } from '../mensaje.model';
import { MensajeFormService } from './mensaje-form.service';

import { MensajeUpdateComponent } from './mensaje-update.component';

describe('Mensaje Management Update Component', () => {
  let comp: MensajeUpdateComponent;
  let fixture: ComponentFixture<MensajeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mensajeFormService: MensajeFormService;
  let mensajeService: MensajeService;
  let jugadorService: JugadorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, MensajeUpdateComponent],
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
      .overrideTemplate(MensajeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MensajeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mensajeFormService = TestBed.inject(MensajeFormService);
    mensajeService = TestBed.inject(MensajeService);
    jugadorService = TestBed.inject(JugadorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Jugador query and add missing value', () => {
      const mensaje: IMensaje = { id: 456 };
      const remitente: IJugador = { id: 30367 };
      mensaje.remitente = remitente;
      const destinatario: IJugador = { id: 19818 };
      mensaje.destinatario = destinatario;

      const jugadorCollection: IJugador[] = [{ id: 19278 }];
      jest.spyOn(jugadorService, 'query').mockReturnValue(of(new HttpResponse({ body: jugadorCollection })));
      const additionalJugadors = [remitente, destinatario];
      const expectedCollection: IJugador[] = [...additionalJugadors, ...jugadorCollection];
      jest.spyOn(jugadorService, 'addJugadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mensaje });
      comp.ngOnInit();

      expect(jugadorService.query).toHaveBeenCalled();
      expect(jugadorService.addJugadorToCollectionIfMissing).toHaveBeenCalledWith(
        jugadorCollection,
        ...additionalJugadors.map(expect.objectContaining),
      );
      expect(comp.jugadorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mensaje: IMensaje = { id: 456 };
      const remitente: IJugador = { id: 25561 };
      mensaje.remitente = remitente;
      const destinatario: IJugador = { id: 11154 };
      mensaje.destinatario = destinatario;

      activatedRoute.data = of({ mensaje });
      comp.ngOnInit();

      expect(comp.jugadorsSharedCollection).toContain(remitente);
      expect(comp.jugadorsSharedCollection).toContain(destinatario);
      expect(comp.mensaje).toEqual(mensaje);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMensaje>>();
      const mensaje = { id: 123 };
      jest.spyOn(mensajeFormService, 'getMensaje').mockReturnValue(mensaje);
      jest.spyOn(mensajeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensaje });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mensaje }));
      saveSubject.complete();

      // THEN
      expect(mensajeFormService.getMensaje).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(mensajeService.update).toHaveBeenCalledWith(expect.objectContaining(mensaje));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMensaje>>();
      const mensaje = { id: 123 };
      jest.spyOn(mensajeFormService, 'getMensaje').mockReturnValue({ id: null });
      jest.spyOn(mensajeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensaje: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mensaje }));
      saveSubject.complete();

      // THEN
      expect(mensajeFormService.getMensaje).toHaveBeenCalled();
      expect(mensajeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IMensaje>>();
      const mensaje = { id: 123 };
      jest.spyOn(mensajeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mensaje });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mensajeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareJugador', () => {
      it('Should forward to jugadorService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(jugadorService, 'compareJugador');
        comp.compareJugador(entity, entity2);
        expect(jugadorService.compareJugador).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
