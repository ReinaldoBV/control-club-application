import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEntrenamiento } from '../entrenamiento.model';
import { EntrenamientoService } from '../service/entrenamiento.service';

@Component({
  standalone: true,
  templateUrl: './entrenamiento-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EntrenamientoDeleteDialogComponent {
  entrenamiento?: IEntrenamiento;

  protected entrenamientoService = inject(EntrenamientoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entrenamientoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
