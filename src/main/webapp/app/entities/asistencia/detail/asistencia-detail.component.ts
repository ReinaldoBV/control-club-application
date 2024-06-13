import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IAsistencia } from '../asistencia.model';

@Component({
  standalone: true,
  selector: 'jhi-asistencia-detail',
  templateUrl: './asistencia-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class AsistenciaDetailComponent {
  asistencia = input<IAsistencia | null>(null);

  previousState(): void {
    window.history.back();
  }
}
