import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICuerpoTecnico } from '../cuerpo-tecnico.model';

@Component({
  standalone: true,
  selector: 'jhi-cuerpo-tecnico-detail',
  templateUrl: './cuerpo-tecnico-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CuerpoTecnicoDetailComponent {
  cuerpoTecnico = input<ICuerpoTecnico | null>(null);

  previousState(): void {
    window.history.back();
  }
}
