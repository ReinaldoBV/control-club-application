import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import { TorneosParticipacionesService } from '../service/torneos-participaciones.service';

@Component({
  standalone: true,
  templateUrl: './torneos-participaciones-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class TorneosParticipacionesDeleteDialogComponent {
  torneosParticipaciones?: ITorneosParticipaciones;

  protected torneosParticipacionesService = inject(TorneosParticipacionesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.torneosParticipacionesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
