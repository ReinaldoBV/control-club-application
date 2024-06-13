import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICuentas } from '../cuentas.model';

@Component({
  standalone: true,
  selector: 'jhi-cuentas-detail',
  templateUrl: './cuentas-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CuentasDetailComponent {
  cuentas = input<ICuentas | null>(null);

  previousState(): void {
    window.history.back();
  }
}
