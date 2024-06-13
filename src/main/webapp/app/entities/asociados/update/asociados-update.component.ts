import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAsociados } from '../asociados.model';
import { AsociadosService } from '../service/asociados.service';
import { AsociadosFormService, AsociadosFormGroup } from './asociados-form.service';

@Component({
  standalone: true,
  selector: 'jhi-asociados-update',
  templateUrl: './asociados-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class AsociadosUpdateComponent implements OnInit {
  isSaving = false;
  asociados: IAsociados | null = null;

  protected asociadosService = inject(AsociadosService);
  protected asociadosFormService = inject(AsociadosFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsociadosFormGroup = this.asociadosFormService.createAsociadosFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asociados }) => {
      this.asociados = asociados;
      if (asociados) {
        this.updateForm(asociados);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const asociados = this.asociadosFormService.getAsociados(this.editForm);
    if (asociados.id !== null) {
      this.subscribeToSaveResponse(this.asociadosService.update(asociados));
    } else {
      this.subscribeToSaveResponse(this.asociadosService.create(asociados));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsociados>>): void {
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

  protected updateForm(asociados: IAsociados): void {
    this.asociados = asociados;
    this.asociadosFormService.resetForm(this.editForm, asociados);
  }
}
