import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPrevisionSalud } from '../prevision-salud.model';

@Component({
  standalone: true,
  selector: 'jhi-prevision-salud-detail',
  templateUrl: './prevision-salud-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PrevisionSaludDetailComponent {
  previsionSalud = input<IPrevisionSalud | null>(null);

  previousState(): void {
    window.history.back();
  }
}
