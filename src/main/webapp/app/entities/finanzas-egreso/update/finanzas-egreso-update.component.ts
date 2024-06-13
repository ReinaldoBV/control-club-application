import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoEgreso } from 'app/entities/enumerations/tipo-egreso.model';
import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';
import { FinanzasEgresoFormService, FinanzasEgresoFormGroup } from './finanzas-egreso-form.service';

@Component({
  standalone: true,
  selector: 'jhi-finanzas-egreso-update',
  templateUrl: './finanzas-egreso-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FinanzasEgresoUpdateComponent implements OnInit {
  isSaving = false;
  finanzasEgreso: IFinanzasEgreso | null = null;
  tipoEgresoValues = Object.keys(TipoEgreso);

  protected finanzasEgresoService = inject(FinanzasEgresoService);
  protected finanzasEgresoFormService = inject(FinanzasEgresoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FinanzasEgresoFormGroup = this.finanzasEgresoFormService.createFinanzasEgresoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finanzasEgreso }) => {
      this.finanzasEgreso = finanzasEgreso;
      if (finanzasEgreso) {
        this.updateForm(finanzasEgreso);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const finanzasEgreso = this.finanzasEgresoFormService.getFinanzasEgreso(this.editForm);
    if (finanzasEgreso.id !== null) {
      this.subscribeToSaveResponse(this.finanzasEgresoService.update(finanzasEgreso));
    } else {
      this.subscribeToSaveResponse(this.finanzasEgresoService.create(finanzasEgreso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinanzasEgreso>>): void {
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

  protected updateForm(finanzasEgreso: IFinanzasEgreso): void {
    this.finanzasEgreso = finanzasEgreso;
    this.finanzasEgresoFormService.resetForm(this.editForm, finanzasEgreso);
  }
}
