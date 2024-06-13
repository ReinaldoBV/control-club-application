import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { ICategorias } from '../categorias.model';
import { CategoriasService } from '../service/categorias.service';
import { CategoriasFormService, CategoriasFormGroup } from './categorias-form.service';

@Component({
  standalone: true,
  selector: 'jhi-categorias-update',
  templateUrl: './categorias-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CategoriasUpdateComponent implements OnInit {
  isSaving = false;
  categorias: ICategorias | null = null;

  protected categoriasService = inject(CategoriasService);
  protected categoriasFormService = inject(CategoriasFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CategoriasFormGroup = this.categoriasFormService.createCategoriasFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categorias }) => {
      this.categorias = categorias;
      if (categorias) {
        this.updateForm(categorias);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categorias = this.categoriasFormService.getCategorias(this.editForm);
    if (categorias.id !== null) {
      this.subscribeToSaveResponse(this.categoriasService.update(categorias));
    } else {
      this.subscribeToSaveResponse(this.categoriasService.create(categorias));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorias>>): void {
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

  protected updateForm(categorias: ICategorias): void {
    this.categorias = categorias;
    this.categoriasFormService.resetForm(this.editForm, categorias);
  }
}
