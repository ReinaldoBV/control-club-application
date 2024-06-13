import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IMensaje } from '../mensaje.model';

@Component({
  standalone: true,
  selector: 'jhi-mensaje-detail',
  templateUrl: './mensaje-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class MensajeDetailComponent {
  mensaje = input<IMensaje | null>(null);

  previousState(): void {
    window.history.back();
  }
}
