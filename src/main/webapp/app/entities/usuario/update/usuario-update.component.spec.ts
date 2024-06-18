import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { IAsociados } from 'app/entities/asociados/asociados.model';
import { AsociadosService } from 'app/entities/asociados/service/asociados.service';
import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { DirectivosService } from 'app/entities/directivos/service/directivos.service';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';
import { CuerpoTecnicoService } from 'app/entities/cuerpo-tecnico/service/cuerpo-tecnico.service';
import { IUsuario } from '../usuario.model';
import { UsuarioService } from '../service/usuario.service';
import { UsuarioFormService } from './usuario-form.service';

import { UsuarioUpdateComponent } from './usuario-update.component';

describe('Usuario Management Update Component', () => {
  let comp: UsuarioUpdateComponent;
  let fixture: ComponentFixture<UsuarioUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let usuarioFormService: UsuarioFormService;
  let usuarioService: UsuarioService;
  let jugadorService: JugadorService;
  let asociadosService: AsociadosService;
  let directivosService: DirectivosService;
  let cuerpoTecnicoService: CuerpoTecnicoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, UsuarioUpdateComponent],
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
      .overrideTemplate(UsuarioUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(UsuarioUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    usuarioFormService = TestBed.inject(UsuarioFormService);
    usuarioService = TestBed.inject(UsuarioService);
    jugadorService = TestBed.inject(JugadorService);
    asociadosService = TestBed.inject(AsociadosService);
    directivosService = TestBed.inject(DirectivosService);
    cuerpoTecnicoService = TestBed.inject(CuerpoTecnicoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call jugador query and add missing value', () => {
      const usuario: IUsuario = { id: 456 };
      const jugador: IJugador = { id: 7614 };
      usuario.jugador = jugador;

      const jugadorCollection: IJugador[] = [{ id: 30295 }];
      jest.spyOn(jugadorService, 'query').mockReturnValue(of(new HttpResponse({ body: jugadorCollection })));
      const expectedCollection: IJugador[] = [jugador, ...jugadorCollection];
      jest.spyOn(jugadorService, 'addJugadorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      expect(jugadorService.query).toHaveBeenCalled();
      expect(jugadorService.addJugadorToCollectionIfMissing).toHaveBeenCalledWith(jugadorCollection, jugador);
      expect(comp.jugadorsCollection).toEqual(expectedCollection);
    });

    it('Should call asociados query and add missing value', () => {
      const usuario: IUsuario = { id: 456 };
      const asociados: IAsociados = { id: 13097 };
      usuario.asociados = asociados;

      const asociadosCollection: IAsociados[] = [{ id: 16748 }];
      jest.spyOn(asociadosService, 'query').mockReturnValue(of(new HttpResponse({ body: asociadosCollection })));
      const expectedCollection: IAsociados[] = [asociados, ...asociadosCollection];
      jest.spyOn(asociadosService, 'addAsociadosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      expect(asociadosService.query).toHaveBeenCalled();
      expect(asociadosService.addAsociadosToCollectionIfMissing).toHaveBeenCalledWith(asociadosCollection, asociados);
      expect(comp.asociadosCollection).toEqual(expectedCollection);
    });

    it('Should call directivos query and add missing value', () => {
      const usuario: IUsuario = { id: 456 };
      const directivos: IDirectivos = { id: 588 };
      usuario.directivos = directivos;

      const directivosCollection: IDirectivos[] = [{ id: 15105 }];
      jest.spyOn(directivosService, 'query').mockReturnValue(of(new HttpResponse({ body: directivosCollection })));
      const expectedCollection: IDirectivos[] = [directivos, ...directivosCollection];
      jest.spyOn(directivosService, 'addDirectivosToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      expect(directivosService.query).toHaveBeenCalled();
      expect(directivosService.addDirectivosToCollectionIfMissing).toHaveBeenCalledWith(directivosCollection, directivos);
      expect(comp.directivosCollection).toEqual(expectedCollection);
    });

    it('Should call cuerpoTecnico query and add missing value', () => {
      const usuario: IUsuario = { id: 456 };
      const cuerpoTecnico: ICuerpoTecnico = { id: 7033 };
      usuario.cuerpoTecnico = cuerpoTecnico;

      const cuerpoTecnicoCollection: ICuerpoTecnico[] = [{ id: 25064 }];
      jest.spyOn(cuerpoTecnicoService, 'query').mockReturnValue(of(new HttpResponse({ body: cuerpoTecnicoCollection })));
      const expectedCollection: ICuerpoTecnico[] = [cuerpoTecnico, ...cuerpoTecnicoCollection];
      jest.spyOn(cuerpoTecnicoService, 'addCuerpoTecnicoToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      expect(cuerpoTecnicoService.query).toHaveBeenCalled();
      expect(cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing).toHaveBeenCalledWith(cuerpoTecnicoCollection, cuerpoTecnico);
      expect(comp.cuerpoTecnicosCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const usuario: IUsuario = { id: 456 };
      const jugador: IJugador = { id: 25326 };
      usuario.jugador = jugador;
      const asociados: IAsociados = { id: 3858 };
      usuario.asociados = asociados;
      const directivos: IDirectivos = { id: 10024 };
      usuario.directivos = directivos;
      const cuerpoTecnico: ICuerpoTecnico = { id: 4851 };
      usuario.cuerpoTecnico = cuerpoTecnico;

      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      expect(comp.jugadorsCollection).toContain(jugador);
      expect(comp.asociadosCollection).toContain(asociados);
      expect(comp.directivosCollection).toContain(directivos);
      expect(comp.cuerpoTecnicosCollection).toContain(cuerpoTecnico);
      expect(comp.usuario).toEqual(usuario);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuario>>();
      const usuario = { id: 123 };
      jest.spyOn(usuarioFormService, 'getUsuario').mockReturnValue(usuario);
      jest.spyOn(usuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuario }));
      saveSubject.complete();

      // THEN
      expect(usuarioFormService.getUsuario).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(usuarioService.update).toHaveBeenCalledWith(expect.objectContaining(usuario));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuario>>();
      const usuario = { id: 123 };
      jest.spyOn(usuarioFormService, 'getUsuario').mockReturnValue({ id: null });
      jest.spyOn(usuarioService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuario: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: usuario }));
      saveSubject.complete();

      // THEN
      expect(usuarioFormService.getUsuario).toHaveBeenCalled();
      expect(usuarioService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IUsuario>>();
      const usuario = { id: 123 };
      jest.spyOn(usuarioService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ usuario });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(usuarioService.update).toHaveBeenCalled();
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

    describe('compareAsociados', () => {
      it('Should forward to asociadosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(asociadosService, 'compareAsociados');
        comp.compareAsociados(entity, entity2);
        expect(asociadosService.compareAsociados).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareDirectivos', () => {
      it('Should forward to directivosService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(directivosService, 'compareDirectivos');
        comp.compareDirectivos(entity, entity2);
        expect(directivosService.compareDirectivos).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCuerpoTecnico', () => {
      it('Should forward to cuerpoTecnicoService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cuerpoTecnicoService, 'compareCuerpoTecnico');
        comp.compareCuerpoTecnico(entity, entity2);
        expect(cuerpoTecnicoService.compareCuerpoTecnico).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
