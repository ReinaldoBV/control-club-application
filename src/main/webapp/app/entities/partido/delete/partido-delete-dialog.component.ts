import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPartido } from '../partido.model';
import { PartidoService } from '../service/partido.service';

@Component({
  standalone: true,
  templateUrl: './partido-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PartidoDeleteDialogComponent {
  partido?: IPartido;

  protected partidoService = inject(PartidoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partidoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
