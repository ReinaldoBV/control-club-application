import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CuentasDetailComponent } from './cuentas-detail.component';

describe('Cuentas Management Detail Component', () => {
  let comp: CuentasDetailComponent;
  let fixture: ComponentFixture<CuentasDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuentasDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CuentasDetailComponent,
              resolve: { cuentas: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CuentasDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CuentasDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cuentas on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CuentasDetailComponent);

      // THEN
      expect(instance.cuentas()).toEqual(expect.objectContaining({ id: 123 }));
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
