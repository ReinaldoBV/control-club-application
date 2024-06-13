import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IEntrenamiento } from '../entrenamiento.model';

@Component({
  standalone: true,
  selector: 'jhi-entrenamiento-detail',
  templateUrl: './entrenamiento-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class EntrenamientoDetailComponent {
  entrenamiento = input<IEntrenamiento | null>(null);

  previousState(): void {
    window.history.back();
  }
}
