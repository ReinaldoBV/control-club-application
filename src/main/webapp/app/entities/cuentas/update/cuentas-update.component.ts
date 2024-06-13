import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { TipoCuenta } from 'app/entities/enumerations/tipo-cuenta.model';
import { EstadoCuenta } from 'app/entities/enumerations/estado-cuenta.model';
import { ICuentas } from '../cuentas.model';
import { CuentasService } from '../service/cuentas.service';
import { CuentasFormService, CuentasFormGroup } from './cuentas-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cuentas-update',
  templateUrl: './cuentas-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CuentasUpdateComponent implements OnInit {
  isSaving = false;
  cuentas: ICuentas | null = null;
  tipoCuentaValues = Object.keys(TipoCuenta);
  estadoCuentaValues = Object.keys(EstadoCuenta);

  protected cuentasService = inject(CuentasService);
  protected cuentasFormService = inject(CuentasFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CuentasFormGroup = this.cuentasFormService.createCuentasFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuentas }) => {
      this.cuentas = cuentas;
      if (cuentas) {
        this.updateForm(cuentas);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cuentas = this.cuentasFormService.getCuentas(this.editForm);
    if (cuentas.id !== null) {
      this.subscribeToSaveResponse(this.cuentasService.update(cuentas));
    } else {
      this.subscribeToSaveResponse(this.cuentasService.create(cuentas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuentas>>): void {
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

  protected updateForm(cuentas: ICuentas): void {
    this.cuentas = cuentas;
    this.cuentasFormService.resetForm(this.editForm, cuentas);
  }
}
