import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';

@Component({
  standalone: true,
  templateUrl: './finanzas-egreso-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FinanzasEgresoDeleteDialogComponent {
  finanzasEgreso?: IFinanzasEgreso;

  protected finanzasEgresoService = inject(FinanzasEgresoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.finanzasEgresoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
