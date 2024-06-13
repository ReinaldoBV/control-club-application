import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IMensaje } from '../mensaje.model';
import { MensajeService } from '../service/mensaje.service';
import { MensajeFormService, MensajeFormGroup } from './mensaje-form.service';

@Component({
  standalone: true,
  selector: 'jhi-mensaje-update',
  templateUrl: './mensaje-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class MensajeUpdateComponent implements OnInit {
  isSaving = false;
  mensaje: IMensaje | null = null;

  protected mensajeService = inject(MensajeService);
  protected mensajeFormService = inject(MensajeFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: MensajeFormGroup = this.mensajeFormService.createMensajeFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mensaje }) => {
      this.mensaje = mensaje;
      if (mensaje) {
        this.updateForm(mensaje);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mensaje = this.mensajeFormService.getMensaje(this.editForm);
    if (mensaje.id !== null) {
      this.subscribeToSaveResponse(this.mensajeService.update(mensaje));
    } else {
      this.subscribeToSaveResponse(this.mensajeService.create(mensaje));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMensaje>>): void {
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

  protected updateForm(mensaje: IMensaje): void {
    this.mensaje = mensaje;
    this.mensajeFormService.resetForm(this.editForm, mensaje);
  }
}
