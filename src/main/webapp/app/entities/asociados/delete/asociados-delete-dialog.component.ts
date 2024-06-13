import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IAsociados } from '../asociados.model';
import { AsociadosService } from '../service/asociados.service';

@Component({
  standalone: true,
  templateUrl: './asociados-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class AsociadosDeleteDialogComponent {
  asociados?: IAsociados;

  protected asociadosService = inject(AsociadosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.asociadosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
