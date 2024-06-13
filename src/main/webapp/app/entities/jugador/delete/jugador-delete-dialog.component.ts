import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IJugador } from '../jugador.model';
import { JugadorService } from '../service/jugador.service';

@Component({
  standalone: true,
  templateUrl: './jugador-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class JugadorDeleteDialogComponent {
  jugador?: IJugador;

  protected jugadorService = inject(JugadorService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jugadorService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
