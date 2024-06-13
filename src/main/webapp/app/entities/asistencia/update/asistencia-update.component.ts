import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoAsistencia } from 'app/entities/enumerations/tipo-asistencia.model';
import { IAsistencia } from '../asistencia.model';
import { AsistenciaService } from '../service/asistencia.service';
import { AsistenciaFormService, AsistenciaFormGroup } from './asistencia-form.service';

@Component({
  standalone: true,
  selector: 'jhi-asistencia-update',
  templateUrl: './asistencia-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AsistenciaUpdateComponent implements OnInit {
  isSaving = false;
  asistencia: IAsistencia | null = null;
  tipoAsistenciaValues = Object.keys(TipoAsistencia);

  protected asistenciaService = inject(AsistenciaService);
  protected asistenciaFormService = inject(AsistenciaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsistenciaFormGroup = this.asistenciaFormService.createAsistenciaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asistencia }) => {
      this.asistencia = asistencia;
      if (asistencia) {
        this.updateForm(asistencia);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const asistencia = this.asistenciaFormService.getAsistencia(this.editForm);
    if (asistencia.id !== null) {
      this.subscribeToSaveResponse(this.asistenciaService.update(asistencia));
    } else {
      this.subscribeToSaveResponse(this.asistenciaService.create(asistencia));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsistencia>>): void {
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

  protected updateForm(asistencia: IAsistencia): void {
    this.asistencia = asistencia;
    this.asistenciaFormService.resetForm(this.editForm, asistencia);
  }
}
