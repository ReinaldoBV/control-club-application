import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
import { TipoIngreso } from 'app/entities/enumerations/tipo-ingreso.model';
import { FinanzasIngresoService } from '../service/finanzas-ingreso.service';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';
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

  jugadorsSharedCollection: IJugador[] = [];

  protected finanzasIngresoService = inject(FinanzasIngresoService);
  protected finanzasIngresoFormService = inject(FinanzasIngresoFormService);
  protected jugadorService = inject(JugadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: FinanzasIngresoFormGroup = this.finanzasIngresoFormService.createFinanzasIngresoFormGroup();

  compareJugador = (o1: IJugador | null, o2: IJugador | null): boolean => this.jugadorService.compareJugador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ finanzasIngreso }) => {
      this.finanzasIngreso = finanzasIngreso;
      if (finanzasIngreso) {
        this.updateForm(finanzasIngreso);
      }

      this.loadRelationshipsOptions();
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

    this.jugadorsSharedCollection = this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(
      this.jugadorsSharedCollection,
      finanzasIngreso.jugador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jugadorService
      .query()
      .pipe(map((res: HttpResponse<IJugador[]>) => res.body ?? []))
      .pipe(
        map((jugadors: IJugador[]) =>
          this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(jugadors, this.finanzasIngreso?.jugador),
        ),
      )
      .subscribe((jugadors: IJugador[]) => (this.jugadorsSharedCollection = jugadors));
  }
}
