import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';

@Component({
  standalone: true,
  selector: 'jhi-finanzas-ingreso-detail',
  templateUrl: './finanzas-ingreso-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class FinanzasIngresoDetailComponent {
  finanzasIngreso = input<IFinanzasIngreso | null>(null);

  previousState(): void {
    window.history.back();
  }
}
