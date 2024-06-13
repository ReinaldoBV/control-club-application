import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IPartido } from '../partido.model';
import { PartidoService } from '../service/partido.service';
import { PartidoFormService, PartidoFormGroup } from './partido-form.service';

@Component({
  standalone: true,
  selector: 'jhi-partido-update',
  templateUrl: './partido-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PartidoUpdateComponent implements OnInit {
  isSaving = false;
  partido: IPartido | null = null;

  protected partidoService = inject(PartidoService);
  protected partidoFormService = inject(PartidoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PartidoFormGroup = this.partidoFormService.createPartidoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partido }) => {
      this.partido = partido;
      if (partido) {
        this.updateForm(partido);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partido = this.partidoFormService.getPartido(this.editForm);
    if (partido.id !== null) {
      this.subscribeToSaveResponse(this.partidoService.update(partido));
    } else {
      this.subscribeToSaveResponse(this.partidoService.create(partido));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartido>>): void {
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

  protected updateForm(partido: IPartido): void {
    this.partido = partido;
    this.partidoFormService.resetForm(this.editForm, partido);
  }
}
