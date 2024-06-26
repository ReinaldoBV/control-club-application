import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IBienes } from '../bienes.model';
import { BienesService } from '../service/bienes.service';
import { BienesFormService, BienesFormGroup } from './bienes-form.service';

@Component({
  standalone: true,
  selector: 'jhi-bienes-update',
  templateUrl: './bienes-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class BienesUpdateComponent implements OnInit {
  isSaving = false;
  bienes: IBienes | null = null;

  protected bienesService = inject(BienesService);
  protected bienesFormService = inject(BienesFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: BienesFormGroup = this.bienesFormService.createBienesFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bienes }) => {
      this.bienes = bienes;
      if (bienes) {
        this.updateForm(bienes);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bienes = this.bienesFormService.getBienes(this.editForm);
    if (bienes.id !== null) {
      this.subscribeToSaveResponse(this.bienesService.update(bienes));
    } else {
      this.subscribeToSaveResponse(this.bienesService.create(bienes));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBienes>>): void {
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

  protected updateForm(bienes: IBienes): void {
    this.bienes = bienes;
    this.bienesFormService.resetForm(this.editForm, bienes);
  }
}
