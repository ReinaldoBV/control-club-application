import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPartido } from '../partido.model';

@Component({
  standalone: true,
  selector: 'jhi-partido-detail',
  templateUrl: './partido-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PartidoDetailComponent {
  partido = input<IPartido | null>(null);

  previousState(): void {
    window.history.back();
  }
}
