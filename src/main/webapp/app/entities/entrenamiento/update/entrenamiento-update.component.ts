import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntrenamiento } from '../entrenamiento.model';
import { EntrenamientoService } from '../service/entrenamiento.service';
import { EntrenamientoFormService, EntrenamientoFormGroup } from './entrenamiento-form.service';

@Component({
  standalone: true,
  selector: 'jhi-entrenamiento-update',
  templateUrl: './entrenamiento-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EntrenamientoUpdateComponent implements OnInit {
  isSaving = false;
  entrenamiento: IEntrenamiento | null = null;

  protected entrenamientoService = inject(EntrenamientoService);
  protected entrenamientoFormService = inject(EntrenamientoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EntrenamientoFormGroup = this.entrenamientoFormService.createEntrenamientoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ entrenamiento }) => {
      this.entrenamiento = entrenamiento;
      if (entrenamiento) {
        this.updateForm(entrenamiento);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const entrenamiento = this.entrenamientoFormService.getEntrenamiento(this.editForm);
    if (entrenamiento.id !== null) {
      this.subscribeToSaveResponse(this.entrenamientoService.update(entrenamiento));
    } else {
      this.subscribeToSaveResponse(this.entrenamientoService.create(entrenamiento));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntrenamiento>>): void {
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

  protected updateForm(entrenamiento: IEntrenamiento): void {
    this.entrenamiento = entrenamiento;
    this.entrenamientoFormService.resetForm(this.editForm, entrenamiento);
  }
}
