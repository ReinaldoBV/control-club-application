import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';
import { EstadisticasBaloncestoService } from '../service/estadisticas-baloncesto.service';
import { EstadisticasBaloncestoFormService, EstadisticasBaloncestoFormGroup } from './estadisticas-baloncesto-form.service';

@Component({
  standalone: true,
  selector: 'jhi-estadisticas-baloncesto-update',
  templateUrl: './estadisticas-baloncesto-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EstadisticasBaloncestoUpdateComponent implements OnInit {
  isSaving = false;
  estadisticasBaloncesto: IEstadisticasBaloncesto | null = null;

  protected estadisticasBaloncestoService = inject(EstadisticasBaloncestoService);
  protected estadisticasBaloncestoFormService = inject(EstadisticasBaloncestoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EstadisticasBaloncestoFormGroup = this.estadisticasBaloncestoFormService.createEstadisticasBaloncestoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ estadisticasBaloncesto }) => {
      this.estadisticasBaloncesto = estadisticasBaloncesto;
      if (estadisticasBaloncesto) {
        this.updateForm(estadisticasBaloncesto);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const estadisticasBaloncesto = this.estadisticasBaloncestoFormService.getEstadisticasBaloncesto(this.editForm);
    if (estadisticasBaloncesto.id !== null) {
      this.subscribeToSaveResponse(this.estadisticasBaloncestoService.update(estadisticasBaloncesto));
    } else {
      this.subscribeToSaveResponse(this.estadisticasBaloncestoService.create(estadisticasBaloncesto));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstadisticasBaloncesto>>): void {
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

  protected updateForm(estadisticasBaloncesto: IEstadisticasBaloncesto): void {
    this.estadisticasBaloncesto = estadisticasBaloncesto;
    this.estadisticasBaloncestoFormService.resetForm(this.editForm, estadisticasBaloncesto);
  }
}
