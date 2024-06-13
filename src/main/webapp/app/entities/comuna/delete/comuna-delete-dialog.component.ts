import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IComuna } from '../comuna.model';
import { ComunaService } from '../service/comuna.service';

@Component({
  standalone: true,
  templateUrl: './comuna-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class ComunaDeleteDialogComponent {
  comuna?: IComuna;

  protected comunaService = inject(ComunaService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.comunaService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
