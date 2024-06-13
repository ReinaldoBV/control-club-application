import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PrevisionSaludDetailComponent } from './prevision-salud-detail.component';

describe('PrevisionSalud Management Detail Component', () => {
  let comp: PrevisionSaludDetailComponent;
  let fixture: ComponentFixture<PrevisionSaludDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrevisionSaludDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PrevisionSaludDetailComponent,
              resolve: { previsionSalud: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PrevisionSaludDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PrevisionSaludDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load previsionSalud on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PrevisionSaludDetailComponent);

      // THEN
      expect(instance.previsionSalud()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
