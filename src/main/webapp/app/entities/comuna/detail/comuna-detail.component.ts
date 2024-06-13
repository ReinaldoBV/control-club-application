import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IComuna } from '../comuna.model';

@Component({
  standalone: true,
  selector: 'jhi-comuna-detail',
  templateUrl: './comuna-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ComunaDetailComponent {
  comuna = input<IComuna | null>(null);

  previousState(): void {
    window.history.back();
  }
}
