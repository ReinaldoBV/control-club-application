import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IBienes } from '../bienes.model';

@Component({
  standalone: true,
  selector: 'jhi-bienes-detail',
  templateUrl: './bienes-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class BienesDetailComponent {
  bienes = input<IBienes | null>(null);

  previousState(): void {
    window.history.back();
  }
}
