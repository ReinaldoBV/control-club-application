import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFinanzasEgreso } from '../finanzas-egreso.model';

@Component({
  standalone: true,
  selector: 'jhi-finanzas-egreso-detail',
  templateUrl: './finanzas-egreso-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FinanzasEgresoDetailComponent {
  finanzasEgreso = input<IFinanzasEgreso | null>(null);

  previousState(): void {
    window.history.back();
  }
}
