import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IBienes } from '../bienes.model';
import { BienesService } from '../service/bienes.service';

@Component({
  standalone: true,
  templateUrl: './bienes-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class BienesDeleteDialogComponent {
  bienes?: IBienes;

  protected bienesService = inject(BienesService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bienesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
