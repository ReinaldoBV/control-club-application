import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IClub } from '../club.model';

@Component({
  standalone: true,
  selector: 'jhi-club-detail',
  templateUrl: './club-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class ClubDetailComponent {
  club = input<IClub | null>(null);

  previousState(): void {
    window.history.back();
  }
}
