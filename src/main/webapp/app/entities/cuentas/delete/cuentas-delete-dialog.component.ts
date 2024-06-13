import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICuentas } from '../cuentas.model';
import { CuentasService } from '../service/cuentas.service';

@Component({
  standalone: true,
  templateUrl: './cuentas-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CuentasDeleteDialogComponent {
  cuentas?: ICuentas;

  protected cuentasService = inject(CuentasService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cuentasService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
