import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAsistencia } from '../asistencia.model';
import { AsistenciaService } from '../service/asistencia.service';

@Component({
  standalone: true,
  templateUrl: './asistencia-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AsistenciaDeleteDialogComponent {
  asistencia?: IAsistencia;

  protected asistenciaService = inject(AsistenciaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.asistenciaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
