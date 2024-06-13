import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICategorias } from '../categorias.model';

@Component({
  standalone: true,
  selector: 'jhi-categorias-detail',
  templateUrl: './categorias-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CategoriasDetailComponent {
  categorias = input<ICategorias | null>(null);

  previousState(): void {
    window.history.back();
  }
}
