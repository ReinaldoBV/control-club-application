import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICentroEducativo } from '../centro-educativo.model';

@Component({
  standalone: true,
  selector: 'jhi-centro-educativo-detail',
  templateUrl: './centro-educativo-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CentroEducativoDetailComponent {
  centroEducativo = input<ICentroEducativo | null>(null);

  previousState(): void {
    window.history.back();
  }
}
