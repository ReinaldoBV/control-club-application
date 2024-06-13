import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPadre } from '../padre.model';

@Component({
  standalone: true,
  selector: 'jhi-padre-detail',
  templateUrl: './padre-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PadreDetailComponent {
  padre = input<IPadre | null>(null);

  previousState(): void {
    window.history.back();
  }
}
