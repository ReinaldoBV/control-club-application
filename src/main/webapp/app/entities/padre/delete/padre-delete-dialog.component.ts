import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPadre } from '../padre.model';
import { PadreService } from '../service/padre.service';

@Component({
  standalone: true,
  templateUrl: './padre-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PadreDeleteDialogComponent {
  padre?: IPadre;

  protected padreService = inject(PadreService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.padreService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
