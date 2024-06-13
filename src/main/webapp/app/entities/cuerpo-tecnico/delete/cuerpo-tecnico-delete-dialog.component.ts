import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { CuerpoTecnicoService } from '../service/cuerpo-tecnico.service';

@Component({
  standalone: true,
  templateUrl: './cuerpo-tecnico-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CuerpoTecnicoDeleteDialogComponent {
  cuerpoTecnico?: ICuerpoTecnico;

  protected cuerpoTecnicoService = inject(CuerpoTecnicoService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cuerpoTecnicoService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
