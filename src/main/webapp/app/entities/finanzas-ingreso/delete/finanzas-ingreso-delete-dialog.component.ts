import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';
import { FinanzasIngresoService } from '../service/finanzas-ingreso.service';

@Component({
  standalone: true,
  templateUrl: './finanzas-ingreso-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class FinanzasIngresoDeleteDialogComponent {
  finanzasIngreso?: IFinanzasIngreso;

  protected finanzasIngresoService = inject(FinanzasIngresoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.finanzasIngresoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
