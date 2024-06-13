import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import { TorneosParticipacionesService } from '../service/torneos-participaciones.service';
import { TorneosParticipacionesFormService, TorneosParticipacionesFormGroup } from './torneos-participaciones-form.service';

@Component({
  standalone: true,
  selector: 'jhi-torneos-participaciones-update',
  templateUrl: './torneos-participaciones-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class TorneosParticipacionesUpdateComponent implements OnInit {
  isSaving = false;
  torneosParticipaciones: ITorneosParticipaciones | null = null;

  protected torneosParticipacionesService = inject(TorneosParticipacionesService);
  protected torneosParticipacionesFormService = inject(TorneosParticipacionesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: TorneosParticipacionesFormGroup = this.torneosParticipacionesFormService.createTorneosParticipacionesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ torneosParticipaciones }) => {
      this.torneosParticipaciones = torneosParticipaciones;
      if (torneosParticipaciones) {
        this.updateForm(torneosParticipaciones);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const torneosParticipaciones = this.torneosParticipacionesFormService.getTorneosParticipaciones(this.editForm);
    if (torneosParticipaciones.id !== null) {
      this.subscribeToSaveResponse(this.torneosParticipacionesService.update(torneosParticipaciones));
    } else {
      this.subscribeToSaveResponse(this.torneosParticipacionesService.create(torneosParticipaciones));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITorneosParticipaciones>>): void {
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

  protected updateForm(torneosParticipaciones: ITorneosParticipaciones): void {
    this.torneosParticipaciones = torneosParticipaciones;
    this.torneosParticipacionesFormService.resetForm(this.editForm, torneosParticipaciones);
  }
}
