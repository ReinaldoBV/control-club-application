import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICuentas } from 'app/entities/cuentas/cuentas.model';
import { CuentasService } from 'app/entities/cuentas/service/cuentas.service';
import { TipoEgreso } from 'app/entities/enumerations/tipo-egreso.model';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';
import { IFinanzasEgreso } from '../finanzas-egreso.model';
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

  cuentasSharedCollection: ICuentas[] = [];

  protected finanzasEgresoService = inject(FinanzasEgresoService);
  protected finanzasEgresoFormService = inject(FinanzasEgresoFormService);
  protected cuentasService = inject(CuentasService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FinanzasEgresoFormGroup = this.finanzasEgresoFormService.createFinanzasEgresoFormGroup();

  compareCuentas = (o1: ICuentas | null, o2: ICuentas | null): boolean => this.cuentasService.compareCuentas(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finanzasEgreso }) => {
      this.finanzasEgreso = finanzasEgreso;
      if (finanzasEgreso) {
        this.updateForm(finanzasEgreso);
      }

      this.loadRelationshipsOptions();
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

    this.cuentasSharedCollection = this.cuentasService.addCuentasToCollectionIfMissing<ICuentas>(
      this.cuentasSharedCollection,
      finanzasEgreso.cuentas,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.cuentasService
      .query()
      .pipe(map((res: HttpResponse<ICuentas[]>) => res.body ?? []))
      .pipe(
        map((cuentas: ICuentas[]) => this.cuentasService.addCuentasToCollectionIfMissing<ICuentas>(cuentas, this.finanzasEgreso?.cuentas)),
      )
      .subscribe((cuentas: ICuentas[]) => (this.cuentasSharedCollection = cuentas));
  }
}
