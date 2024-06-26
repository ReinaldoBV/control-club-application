import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPadre } from '../padre.model';
import { PadreService } from '../service/padre.service';
import { PadreFormService, PadreFormGroup } from './padre-form.service';

@Component({
  standalone: true,
  selector: 'jhi-padre-update',
  templateUrl: './padre-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PadreUpdateComponent implements OnInit {
  isSaving = false;
  padre: IPadre | null = null;

  protected padreService = inject(PadreService);
  protected padreFormService = inject(PadreFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PadreFormGroup = this.padreFormService.createPadreFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ padre }) => {
      this.padre = padre;
      if (padre) {
        this.updateForm(padre);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const padre = this.padreFormService.getPadre(this.editForm);
    if (padre.id !== null) {
      this.subscribeToSaveResponse(this.padreService.update(padre));
    } else {
      this.subscribeToSaveResponse(this.padreService.create(padre));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPadre>>): void {
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

  protected updateForm(padre: IPadre): void {
    this.padre = padre;
    this.padreFormService.resetForm(this.editForm, padre);
  }
}
