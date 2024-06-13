import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICentroEducativo } from '../centro-educativo.model';
import { CentroEducativoService } from '../service/centro-educativo.service';
import { CentroEducativoFormService, CentroEducativoFormGroup } from './centro-educativo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centro-educativo-update',
  templateUrl: './centro-educativo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentroEducativoUpdateComponent implements OnInit {
  isSaving = false;
  centroEducativo: ICentroEducativo | null = null;

  protected centroEducativoService = inject(CentroEducativoService);
  protected centroEducativoFormService = inject(CentroEducativoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentroEducativoFormGroup = this.centroEducativoFormService.createCentroEducativoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroEducativo }) => {
      this.centroEducativo = centroEducativo;
      if (centroEducativo) {
        this.updateForm(centroEducativo);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centroEducativo = this.centroEducativoFormService.getCentroEducativo(this.editForm);
    if (centroEducativo.id !== null) {
      this.subscribeToSaveResponse(this.centroEducativoService.update(centroEducativo));
    } else {
      this.subscribeToSaveResponse(this.centroEducativoService.create(centroEducativo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentroEducativo>>): void {
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

  protected updateForm(centroEducativo: ICentroEducativo): void {
    this.centroEducativo = centroEducativo;
    this.centroEducativoFormService.resetForm(this.editForm, centroEducativo);
  }
}
