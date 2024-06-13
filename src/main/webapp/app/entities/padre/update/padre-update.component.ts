import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IJugador } from 'app/entities/jugador/jugador.model';
import { JugadorService } from 'app/entities/jugador/service/jugador.service';
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

  jugadorsSharedCollection: IJugador[] = [];

  protected padreService = inject(PadreService);
  protected padreFormService = inject(PadreFormService);
  protected jugadorService = inject(JugadorService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PadreFormGroup = this.padreFormService.createPadreFormGroup();

  compareJugador = (o1: IJugador | null, o2: IJugador | null): boolean => this.jugadorService.compareJugador(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ padre }) => {
      this.padre = padre;
      if (padre) {
        this.updateForm(padre);
      }

      this.loadRelationshipsOptions();
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

    this.jugadorsSharedCollection = this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(
      this.jugadorsSharedCollection,
      padre.jugador,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.jugadorService
      .query()
      .pipe(map((res: HttpResponse<IJugador[]>) => res.body ?? []))
      .pipe(map((jugadors: IJugador[]) => this.jugadorService.addJugadorToCollectionIfMissing<IJugador>(jugadors, this.padre?.jugador)))
      .subscribe((jugadors: IJugador[]) => (this.jugadorsSharedCollection = jugadors));
  }
}
