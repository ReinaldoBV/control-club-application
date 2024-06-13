import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IPrevisionSalud } from '../prevision-salud.model';
import { PrevisionSaludService } from '../service/prevision-salud.service';

@Component({
  standalone: true,
  templateUrl: './prevision-salud-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class PrevisionSaludDeleteDialogComponent {
  previsionSalud?: IPrevisionSalud;

  protected previsionSaludService = inject(PrevisionSaludService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.previsionSaludService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
