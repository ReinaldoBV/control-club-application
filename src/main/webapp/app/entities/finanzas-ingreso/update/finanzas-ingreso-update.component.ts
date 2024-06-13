import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoIngreso } from 'app/entities/enumerations/tipo-ingreso.model';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';
import { FinanzasIngresoService } from '../service/finanzas-ingreso.service';
import { FinanzasIngresoFormService, FinanzasIngresoFormGroup } from './finanzas-ingreso-form.service';

@Component({
  standalone: true,
  selector: 'jhi-finanzas-ingreso-update',
  templateUrl: './finanzas-ingreso-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class FinanzasIngresoUpdateComponent implements OnInit {
  isSaving = false;
  finanzasIngreso: IFinanzasIngreso | null = null;
  tipoIngresoValues = Object.keys(TipoIngreso);

  protected finanzasIngresoService = inject(FinanzasIngresoService);
  protected finanzasIngresoFormService = inject(FinanzasIngresoFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FinanzasIngresoFormGroup = this.finanzasIngresoFormService.createFinanzasIngresoFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finanzasIngreso }) => {
      this.finanzasIngreso = finanzasIngreso;
      if (finanzasIngreso) {
        this.updateForm(finanzasIngreso);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const finanzasIngreso = this.finanzasIngresoFormService.getFinanzasIngreso(this.editForm);
    if (finanzasIngreso.id !== null) {
      this.subscribeToSaveResponse(this.finanzasIngresoService.update(finanzasIngreso));
    } else {
      this.subscribeToSaveResponse(this.finanzasIngresoService.create(finanzasIngreso));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFinanzasIngreso>>): void {
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

  protected updateForm(finanzasIngreso: IFinanzasIngreso): void {
    this.finanzasIngreso = finanzasIngreso;
    this.finanzasIngresoFormService.resetForm(this.editForm, finanzasIngreso);
  }
}
