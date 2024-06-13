import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAsociados } from 'app/entities/asociados/asociados.model';
import { AsociadosService } from 'app/entities/asociados/service/asociados.service';
import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { CuerpoTecnicoService } from '../service/cuerpo-tecnico.service';
import { CuerpoTecnicoFormService, CuerpoTecnicoFormGroup } from './cuerpo-tecnico-form.service';

@Component({
  standalone: true,
  selector: 'jhi-cuerpo-tecnico-update',
  templateUrl: './cuerpo-tecnico-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CuerpoTecnicoUpdateComponent implements OnInit {
  isSaving = false;
  cuerpoTecnico: ICuerpoTecnico | null = null;

  asociadosCollection: IAsociados[] = [];

  protected cuerpoTecnicoService = inject(CuerpoTecnicoService);
  protected cuerpoTecnicoFormService = inject(CuerpoTecnicoFormService);
  protected asociadosService = inject(AsociadosService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CuerpoTecnicoFormGroup = this.cuerpoTecnicoFormService.createCuerpoTecnicoFormGroup();

  compareAsociados = (o1: IAsociados | null, o2: IAsociados | null): boolean => this.asociadosService.compareAsociados(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuerpoTecnico }) => {
      this.cuerpoTecnico = cuerpoTecnico;
      if (cuerpoTecnico) {
        this.updateForm(cuerpoTecnico);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cuerpoTecnico = this.cuerpoTecnicoFormService.getCuerpoTecnico(this.editForm);
    if (cuerpoTecnico.id !== null) {
      this.subscribeToSaveResponse(this.cuerpoTecnicoService.update(cuerpoTecnico));
    } else {
      this.subscribeToSaveResponse(this.cuerpoTecnicoService.create(cuerpoTecnico));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuerpoTecnico>>): void {
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

  protected updateForm(cuerpoTecnico: ICuerpoTecnico): void {
    this.cuerpoTecnico = cuerpoTecnico;
    this.cuerpoTecnicoFormService.resetForm(this.editForm, cuerpoTecnico);

    this.asociadosCollection = this.asociadosService.addAsociadosToCollectionIfMissing<IAsociados>(
      this.asociadosCollection,
      cuerpoTecnico.asociados,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.asociadosService
      .query({ filter: 'cuerpotecnico-is-null' })
      .pipe(map((res: HttpResponse<IAsociados[]>) => res.body ?? []))
      .pipe(
        map((asociados: IAsociados[]) =>
          this.asociadosService.addAsociadosToCollectionIfMissing<IAsociados>(asociados, this.cuerpoTecnico?.asociados),
        ),
      )
      .subscribe((asociados: IAsociados[]) => (this.asociadosCollection = asociados));
  }
}
