import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IComuna } from 'app/entities/comuna/comuna.model';
import { ComunaService } from 'app/entities/comuna/service/comuna.service';
import { ICentroEducativo } from '../centro-educativo.model';
import { CentroEducativoService } from '../service/centro-educativo.service';
import { CentroEducativoFormService, CentroEducativoFormGroup } from './centro-educativo-form.service';

@Component({
  standalone: true,
  selector: 'jhi-centro-educativo-update',
  templateUrl: './centro-educativo-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CentroEducativoUpdateComponent implements OnInit {
  isSaving = false;
  centroEducativo: ICentroEducativo | null = null;

  comunasSharedCollection: IComuna[] = [];

  protected centroEducativoService = inject(CentroEducativoService);
  protected centroEducativoFormService = inject(CentroEducativoFormService);
  protected comunaService = inject(ComunaService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CentroEducativoFormGroup = this.centroEducativoFormService.createCentroEducativoFormGroup();

  compareComuna = (o1: IComuna | null, o2: IComuna | null): boolean => this.comunaService.compareComuna(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ centroEducativo }) => {
      this.centroEducativo = centroEducativo;
      if (centroEducativo) {
        this.updateForm(centroEducativo);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const centroEducativo = this.centroEducativoFormService.getCentroEducativo(this.editForm);
    if (centroEducativo.id !== null) {
      this.subscribeToSaveResponse(this.centroEducativoService.update(centroEducativo));
    } else {
      this.subscribeToSaveResponse(this.centroEducativoService.create(centroEducativo));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICentroEducativo>>): void {
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

  protected updateForm(centroEducativo: ICentroEducativo): void {
    this.centroEducativo = centroEducativo;
    this.centroEducativoFormService.resetForm(this.editForm, centroEducativo);

    this.comunasSharedCollection = this.comunaService.addComunaToCollectionIfMissing<IComuna>(
      this.comunasSharedCollection,
      centroEducativo.comuna,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.comunaService
      .query()
      .pipe(map((res: HttpResponse<IComuna[]>) => res.body ?? []))
      .pipe(map((comunas: IComuna[]) => this.comunaService.addComunaToCollectionIfMissing<IComuna>(comunas, this.centroEducativo?.comuna)))
      .subscribe((comunas: IComuna[]) => (this.comunasSharedCollection = comunas));
  }
}
