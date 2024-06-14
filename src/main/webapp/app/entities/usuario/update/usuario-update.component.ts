import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { IAsociados } from 'app/entities/asociados/asociados.model';
import { AsociadosService } from 'app/entities/asociados/service/asociados.service';
import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { DirectivosService } from 'app/entities/directivos/service/directivos.service';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';
import { CuerpoTecnicoService } from 'app/entities/cuerpo-tecnico/service/cuerpo-tecnico.service';
import { RolUsuario } from 'app/entities/enumerations/rol-usuario.model';
import { UsuarioService } from '../service/usuario.service';
import { IUsuario } from '../usuario.model';
import { UsuarioFormService, UsuarioFormGroup } from './usuario-form.service';

@Component({
  standalone: true,
  selector: 'jhi-usuario-update',
  templateUrl: './usuario-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class UsuarioUpdateComponent implements OnInit {
  isSaving = false;
  usuario: IUsuario | null = null;
  rolUsuarioValues = Object.keys(RolUsuario);

  jugadorsCollection: IJugador[] = [];
  asociadosCollection: IAsociados[] = [];
  directivosCollection: IDirectivos[] = [];
  cuerpoTecnicosCollection: ICuerpoTecnico[] = [];

  protected usuarioService = inject(UsuarioService);
  protected usuarioFormService = inject(UsuarioFormService);
  protected jugadorService = inject(JugadorService);
  protected asociadosService = inject(AsociadosService);
  protected directivosService = inject(DirectivosService);
  protected cuerpoTecnicoService = inject(CuerpoTecnicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: UsuarioFormGroup = this.usuarioFormService.createUsuarioFormGroup();

  compareJugador = (o1: IJugador | null, o2: IJugador | null): boolean => this.jugadorService.compareJugador(o1, o2);

  compareAsociados = (o1: IAsociados | null, o2: IAsociados | null): boolean => this.asociadosService.compareAsociados(o1, o2);

  compareDirectivos = (o1: IDirectivos | null, o2: IDirectivos | null): boolean => this.directivosService.compareDirectivos(o1, o2);

  compareCuerpoTecnico = (o1: ICuerpoTecnico | null, o2: ICuerpoTecnico | null): boolean =>
    this.cuerpoTecnicoService.compareCuerpoTecnico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ usuario }) => {
      this.usuario = usuario;
      if (usuario) {
        this.updateForm(usuario);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const usuario = this.usuarioFormService.getUsuario(this.editForm);
    if (usuario.id !== null) {
      this.subscribeToSaveResponse(this.usuarioService.update(usuario));
    } else {
      this.subscribeToSaveResponse(this.usuarioService.create(usuario));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUsuario>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(usuario: IUsuario): void {
    this.usuario = usuario;
    this.usuarioFormService.resetForm(this.editForm, usuario);

    this.jugadorsCollection = this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(this.jugadorsCollection, usuario.jugador);
    this.asociadosCollection = this.asociadosService.addAsociadosToCollectionIfMissing<IAsociados>(
      this.asociadosCollection,
      usuario.asociados,
    );
    this.directivosCollection = this.directivosService.addDirectivosToCollectionIfMissing<IDirectivos>(
      this.directivosCollection,
      usuario.directivos,
    );
    this.cuerpoTecnicosCollection = this.cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing<ICuerpoTecnico>(
      this.cuerpoTecnicosCollection,
      usuario.cuerpoTecnico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jugadorService
      .query({ filter: 'usuario-is-null' })
      .pipe(map((res: HttpResponse<IJugador[]>) => res.body ?? []))
      .pipe(map((jugadors: IJugador[]) => this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(jugadors, this.usuario?.jugador)))
      .subscribe((jugadors: IJugador[]) => (this.jugadorsCollection = jugadors));

    this.asociadosService
      .query({ filter: 'usuario-is-null' })
      .pipe(map((res: HttpResponse<IAsociados[]>) => res.body ?? []))
      .pipe(
        map((asociados: IAsociados[]) =>
          this.asociadosService.addAsociadosToCollectionIfMissing<IAsociados>(asociados, this.usuario?.asociados),
        ),
      )
      .subscribe((asociados: IAsociados[]) => (this.asociadosCollection = asociados));

    this.directivosService
      .query({ filter: 'usuario-is-null' })
      .pipe(map((res: HttpResponse<IDirectivos[]>) => res.body ?? []))
      .pipe(
        map((directivos: IDirectivos[]) =>
          this.directivosService.addDirectivosToCollectionIfMissing<IDirectivos>(directivos, this.usuario?.directivos),
        ),
      )
      .subscribe((directivos: IDirectivos[]) => (this.directivosCollection = directivos));

    this.cuerpoTecnicoService
      .query({ filter: 'usuario-is-null' })
      .pipe(map((res: HttpResponse<ICuerpoTecnico[]>) => res.body ?? []))
      .pipe(
        map((cuerpoTecnicos: ICuerpoTecnico[]) =>
          this.cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing<ICuerpoTecnico>(cuerpoTecnicos, this.usuario?.cuerpoTecnico),
        ),
      )
      .subscribe((cuerpoTecnicos: ICuerpoTecnico[]) => (this.cuerpoTecnicosCollection = cuerpoTecnicos));
  }
}
