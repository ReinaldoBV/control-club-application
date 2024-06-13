import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoPrevision } from 'app/entities/enumerations/tipo-prevision.model';
import { IPrevisionSalud } from '../prevision-salud.model';
import { PrevisionSaludService } from '../service/prevision-salud.service';
import { PrevisionSaludFormService, PrevisionSaludFormGroup } from './prevision-salud-form.service';

@Component({
  standalone: true,
  selector: 'jhi-prevision-salud-update',
  templateUrl: './prevision-salud-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PrevisionSaludUpdateComponent implements OnInit {
  isSaving = false;
  previsionSalud: IPrevisionSalud | null = null;
  tipoPrevisionValues = Object.keys(TipoPrevision);

  protected previsionSaludService = inject(PrevisionSaludService);
  protected previsionSaludFormService = inject(PrevisionSaludFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PrevisionSaludFormGroup = this.previsionSaludFormService.createPrevisionSaludFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ previsionSalud }) => {
      this.previsionSalud = previsionSalud;
      if (previsionSalud) {
        this.updateForm(previsionSalud);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const previsionSalud = this.previsionSaludFormService.getPrevisionSalud(this.editForm);
    if (previsionSalud.id !== null) {
      this.subscribeToSaveResponse(this.previsionSaludService.update(previsionSalud));
    } else {
      this.subscribeToSaveResponse(this.previsionSaludService.create(previsionSalud));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrevisionSalud>>): void {
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

  protected updateForm(previsionSalud: IPrevisionSalud): void {
    this.previsionSalud = previsionSalud;
    this.previsionSaludFormService.resetForm(this.editForm, previsionSalud);
  }
}
