import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IDirectivos } from '../directivos.model';
import { DirectivosService } from '../service/directivos.service';

@Component({
  standalone: true,
  templateUrl: './directivos-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class DirectivosDeleteDialogComponent {
  directivos?: IDirectivos;

  protected directivosService = inject(DirectivosService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.directivosService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
