import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IComuna } from '../comuna.model';
import { ComunaService } from '../service/comuna.service';
import { ComunaFormService, ComunaFormGroup } from './comuna-form.service';

@Component({
  standalone: true,
  selector: 'jhi-comuna-update',
  templateUrl: './comuna-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ComunaUpdateComponent implements OnInit {
  isSaving = false;
  comuna: IComuna | null = null;

  protected comunaService = inject(ComunaService);
  protected comunaFormService = inject(ComunaFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ComunaFormGroup = this.comunaFormService.createComunaFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ comuna }) => {
      this.comuna = comuna;
      if (comuna) {
        this.updateForm(comuna);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const comuna = this.comunaFormService.getComuna(this.editForm);
    if (comuna.id !== null) {
      this.subscribeToSaveResponse(this.comunaService.update(comuna));
    } else {
      this.subscribeToSaveResponse(this.comunaService.create(comuna));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IComuna>>): void {
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

  protected updateForm(comuna: IComuna): void {
    this.comuna = comuna;
    this.comunaFormService.resetForm(this.editForm, comuna);
  }
}
