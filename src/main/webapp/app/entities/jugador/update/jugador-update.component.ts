import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
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

  protected dataUtils = inject(DataUtils);
  protected eventManager = inject(EventManager);
  protected jugadorService = inject(JugadorService);
  protected jugadorFormService = inject(JugadorFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: JugadorFormGroup = this.jugadorFormService.createJugadorFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ jugador }) => {
      this.jugador = jugador;
      if (jugador) {
        this.updateForm(jugador);
      }
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
  }
}
