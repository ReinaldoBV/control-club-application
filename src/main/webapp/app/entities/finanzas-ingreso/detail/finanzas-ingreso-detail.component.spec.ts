import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FinanzasIngresoDetailComponent } from './finanzas-ingreso-detail.component';

describe('FinanzasIngreso Management Detail Component', () => {
  let comp: FinanzasIngresoDetailComponent;
  let fixture: ComponentFixture<FinanzasIngresoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinanzasIngresoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: FinanzasIngresoDetailComponent,
              resolve: { finanzasIngreso: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FinanzasIngresoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FinanzasIngresoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load finanzasIngreso on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FinanzasIngresoDetailComponent);

      // THEN
      expect(instance.finanzasIngreso()).toEqual(expect.objectContaining({ id: 123 }));
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
