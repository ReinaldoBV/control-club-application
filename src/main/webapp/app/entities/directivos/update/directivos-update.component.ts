import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDirectivos } from '../directivos.model';
import { DirectivosService } from '../service/directivos.service';
import { DirectivosFormService, DirectivosFormGroup } from './directivos-form.service';

@Component({
  standalone: true,
  selector: 'jhi-directivos-update',
  templateUrl: './directivos-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class DirectivosUpdateComponent implements OnInit {
  isSaving = false;
  directivos: IDirectivos | null = null;

  protected directivosService = inject(DirectivosService);
  protected directivosFormService = inject(DirectivosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: DirectivosFormGroup = this.directivosFormService.createDirectivosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ directivos }) => {
      this.directivos = directivos;
      if (directivos) {
        this.updateForm(directivos);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const directivos = this.directivosFormService.getDirectivos(this.editForm);
    if (directivos.id !== null) {
      this.subscribeToSaveResponse(this.directivosService.update(directivos));
    } else {
      this.subscribeToSaveResponse(this.directivosService.create(directivos));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDirectivos>>): void {
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

  protected updateForm(directivos: IDirectivos): void {
    this.directivos = directivos;
    this.directivosFormService.resetForm(this.editForm, directivos);
  }
}
