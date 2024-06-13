import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FinanzasEgresoDetailComponent } from './finanzas-egreso-detail.component';

describe('FinanzasEgreso Management Detail Component', () => {
  let comp: FinanzasEgresoDetailComponent;
  let fixture: ComponentFixture<FinanzasEgresoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinanzasEgresoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FinanzasEgresoDetailComponent,
              resolve: { finanzasEgreso: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FinanzasEgresoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FinanzasEgresoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load finanzasEgreso on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FinanzasEgresoDetailComponent);

      // THEN
      expect(instance.finanzasEgreso()).toEqual(expect.objectContaining({ id: 123 }));
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
