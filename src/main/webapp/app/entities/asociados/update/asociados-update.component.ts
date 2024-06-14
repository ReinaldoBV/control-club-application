import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IDirectivos } from 'app/entities/directivos/directivos.model';
import { DirectivosService } from 'app/entities/directivos/service/directivos.service';
import { ICuerpoTecnico } from 'app/entities/cuerpo-tecnico/cuerpo-tecnico.model';
import { CuerpoTecnicoService } from 'app/entities/cuerpo-tecnico/service/cuerpo-tecnico.service';
import { AsociadosService } from '../service/asociados.service';
import { IAsociados } from '../asociados.model';
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

  directivosCollection: IDirectivos[] = [];
  cuerpoTecnicosCollection: ICuerpoTecnico[] = [];

  protected asociadosService = inject(AsociadosService);
  protected asociadosFormService = inject(AsociadosFormService);
  protected directivosService = inject(DirectivosService);
  protected cuerpoTecnicoService = inject(CuerpoTecnicoService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AsociadosFormGroup = this.asociadosFormService.createAsociadosFormGroup();

  compareDirectivos = (o1: IDirectivos | null, o2: IDirectivos | null): boolean => this.directivosService.compareDirectivos(o1, o2);

  compareCuerpoTecnico = (o1: ICuerpoTecnico | null, o2: ICuerpoTecnico | null): boolean =>
    this.cuerpoTecnicoService.compareCuerpoTecnico(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ asociados }) => {
      this.asociados = asociados;
      if (asociados) {
        this.updateForm(asociados);
      }

      this.loadRelationshipsOptions();
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

    this.directivosCollection = this.directivosService.addDirectivosToCollectionIfMissing<IDirectivos>(
      this.directivosCollection,
      asociados.directivos,
    );
    this.cuerpoTecnicosCollection = this.cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing<ICuerpoTecnico>(
      this.cuerpoTecnicosCollection,
      asociados.cuerpoTecnico,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.directivosService
      .query({ filter: 'asociados-is-null' })
      .pipe(map((res: HttpResponse<IDirectivos[]>) => res.body ?? []))
      .pipe(
        map((directivos: IDirectivos[]) =>
          this.directivosService.addDirectivosToCollectionIfMissing<IDirectivos>(directivos, this.asociados?.directivos),
        ),
      )
      .subscribe((directivos: IDirectivos[]) => (this.directivosCollection = directivos));

    this.cuerpoTecnicoService
      .query({ filter: 'asociados-is-null' })
      .pipe(map((res: HttpResponse<ICuerpoTecnico[]>) => res.body ?? []))
      .pipe(
        map((cuerpoTecnicos: ICuerpoTecnico[]) =>
          this.cuerpoTecnicoService.addCuerpoTecnicoToCollectionIfMissing<ICuerpoTecnico>(cuerpoTecnicos, this.asociados?.cuerpoTecnico),
        ),
      )
      .subscribe((cuerpoTecnicos: ICuerpoTecnico[]) => (this.cuerpoTecnicosCollection = cuerpoTecnicos));
  }
}
