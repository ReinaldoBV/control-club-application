import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';

@Component({
  standalone: true,
  selector: 'jhi-estadisticas-baloncesto-detail',
  templateUrl: './estadisticas-baloncesto-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EstadisticasBaloncestoDetailComponent {
  estadisticasBaloncesto = input<IEstadisticasBaloncesto | null>(null);

  previousState(): void {
    window.history.back();
  }
}
