import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICentroEducativo } from '../centro-educativo.model';
import { CentroEducativoService } from '../service/centro-educativo.service';

@Component({
  standalone: true,
  templateUrl: './centro-educativo-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CentroEducativoDeleteDialogComponent {
  centroEducativo?: ICentroEducativo;

  protected centroEducativoService = inject(CentroEducativoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.centroEducativoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
