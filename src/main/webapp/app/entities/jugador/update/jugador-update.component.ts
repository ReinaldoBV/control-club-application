import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ICentroSalud } from 'app/entities/centro-salud/centro-salud.model';
import { CentroSaludService } from 'app/entities/centro-salud/service/centro-salud.service';
import { IPrevisionSalud } from 'app/entities/prevision-salud/prevision-salud.model';
import { PrevisionSaludService } from 'app/entities/prevision-salud/service/prevision-salud.service';
import { ICentroEducativo } from 'app/entities/centro-educativo/centro-educativo.model';
import { CentroEducativoService } from 'app/entities/centro-educativo/service/centro-educativo.service';
import { ICategorias } from 'app/entities/categorias/categorias.model';
import { CategoriasService } from 'app/entities/categorias/service/categorias.service';
import { TipoIdentificacion } from 'app/entities/enumerations/tipo-identificacion.model';
import { Nacionalidad } from 'app/entities/enumerations/nacionalidad.model';
import { JugadorService } from '../service/jugador.service';
import { IJugador } from '../jugador.model';
import { JugadorFormService, JugadorFormGroup } from './jugador-form.service';

@Component({
  standalone: true,
  selector: 'jhi-jugador-update',
  templateUrl: './jugador-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class JugadorUpdateComponent implements OnInit {
  isSaving = false;
  jugador: IJugador | null = null;
  tipoIdentificacionValues = Object.keys(TipoIdentificacion);
  nacionalidadValues = Object.keys(Nacionalidad);

  centroSaludsCollection: ICentroSalud[] = [];
  previsionSaludsCollection: IPrevisionSalud[] = [];
  centroEducativosCollection: ICentroEducativo[] = [];
  categoriasCollection: ICategorias[] = [];

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected jugadorService = inject(JugadorService);
  protected jugadorFormService = inject(JugadorFormService);
  protected centroSaludService = inject(CentroSaludService);
  protected previsionSaludService = inject(PrevisionSaludService);
  protected centroEducativoService = inject(CentroEducativoService);
  protected categoriasService = inject(CategoriasService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: JugadorFormGroup = this.jugadorFormService.createJugadorFormGroup();

  compareCentroSalud = (o1: ICentroSalud | null, o2: ICentroSalud | null): boolean => this.centroSaludService.compareCentroSalud(o1, o2);

  comparePrevisionSalud = (o1: IPrevisionSalud | null, o2: IPrevisionSalud | null): boolean =>
    this.previsionSaludService.comparePrevisionSalud(o1, o2);

  compareCentroEducativo = (o1: ICentroEducativo | null, o2: ICentroEducativo | null): boolean =>
    this.centroEducativoService.compareCentroEducativo(o1, o2);

  compareCategorias = (o1: ICategorias | null, o2: ICategorias | null): boolean => this.categoriasService.compareCategorias(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jugador }) => {
      this.jugador = jugador;
      if (jugador) {
        this.updateForm(jugador);
      }

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(
          new EventWithContent<AlertError>('controlClubApplicationApp.error', { ...err, key: 'error.file.' + err.key }),
        ),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const jugador = this.jugadorFormService.getJugador(this.editForm);
    if (jugador.id !== null) {
      this.subscribeToSaveResponse(this.jugadorService.update(jugador));
    } else {
      this.subscribeToSaveResponse(this.jugadorService.create(jugador));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJugador>>): void {
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

  protected updateForm(jugador: IJugador): void {
    this.jugador = jugador;
    this.jugadorFormService.resetForm(this.editForm, jugador);

    this.centroSaludsCollection = this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(
      this.centroSaludsCollection,
      jugador.centroSalud,
    );
    this.previsionSaludsCollection = this.previsionSaludService.addPrevisionSaludToCollectionIfMissing<IPrevisionSalud>(
      this.previsionSaludsCollection,
      jugador.previsionSalud,
    );
    this.centroEducativosCollection = this.centroEducativoService.addCentroEducativoToCollectionIfMissing<ICentroEducativo>(
      this.centroEducativosCollection,
      jugador.centroEducativo,
    );
    this.categoriasCollection = this.categoriasService.addCategoriasToCollectionIfMissing<ICategorias>(
      this.categoriasCollection,
      jugador.categorias,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.centroSaludService
      .query({ filter: 'jugador-is-null' })
      .pipe(map((res: HttpResponse<ICentroSalud[]>) => res.body ?? []))
      .pipe(
        map((centroSaluds: ICentroSalud[]) =>
          this.centroSaludService.addCentroSaludToCollectionIfMissing<ICentroSalud>(centroSaluds, this.jugador?.centroSalud),
        ),
      )
      .subscribe((centroSaluds: ICentroSalud[]) => (this.centroSaludsCollection = centroSaluds));

    this.previsionSaludService
      .query({ filter: 'jugador-is-null' })
      .pipe(map((res: HttpResponse<IPrevisionSalud[]>) => res.body ?? []))
      .pipe(
        map((previsionSaluds: IPrevisionSalud[]) =>
          this.previsionSaludService.addPrevisionSaludToCollectionIfMissing<IPrevisionSalud>(previsionSaluds, this.jugador?.previsionSalud),
        ),
      )
      .subscribe((previsionSaluds: IPrevisionSalud[]) => (this.previsionSaludsCollection = previsionSaluds));

    this.centroEducativoService
      .query({ filter: 'jugador-is-null' })
      .pipe(map((res: HttpResponse<ICentroEducativo[]>) => res.body ?? []))
      .pipe(
        map((centroEducativos: ICentroEducativo[]) =>
          this.centroEducativoService.addCentroEducativoToCollectionIfMissing<ICentroEducativo>(
            centroEducativos,
            this.jugador?.centroEducativo,
          ),
        ),
      )
      .subscribe((centroEducativos: ICentroEducativo[]) => (this.centroEducativosCollection = centroEducativos));

    this.categoriasService
      .query({ filter: 'jugador-is-null' })
      .pipe(map((res: HttpResponse<ICategorias[]>) => res.body ?? []))
      .pipe(
        map((categorias: ICategorias[]) =>
          this.categoriasService.addCategoriasToCollectionIfMissing<ICategorias>(categorias, this.jugador?.categorias),
        ),
      )
      .subscribe((categorias: ICategorias[]) => (this.categoriasCollection = categorias));
  }
}
