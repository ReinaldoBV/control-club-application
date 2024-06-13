import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';
import { EstadisticasBaloncestoService } from '../service/estadisticas-baloncesto.service';

@Component({
  standalone: true,
  templateUrl: './estadisticas-baloncesto-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EstadisticasBaloncestoDeleteDialogComponent {
  estadisticasBaloncesto?: IEstadisticasBaloncesto;

  protected estadisticasBaloncestoService = inject(EstadisticasBaloncestoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.estadisticasBaloncestoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
