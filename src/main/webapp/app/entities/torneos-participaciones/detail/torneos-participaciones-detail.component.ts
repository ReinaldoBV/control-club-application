import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ITorneosParticipaciones } from '../torneos-participaciones.model';

@Component({
  standalone: true,
  selector: 'jhi-torneos-participaciones-detail',
  templateUrl: './torneos-participaciones-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class TorneosParticipacionesDetailComponent {
  torneosParticipaciones = input<ITorneosParticipaciones | null>(null);

  previousState(): void {
    window.history.back();
  }
}
