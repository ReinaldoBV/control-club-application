import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EstadisticasBaloncestoDetailComponent } from './estadisticas-baloncesto-detail.component';

describe('EstadisticasBaloncesto Management Detail Component', () => {
  let comp: EstadisticasBaloncestoDetailComponent;
  let fixture: ComponentFixture<EstadisticasBaloncestoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadisticasBaloncestoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EstadisticasBaloncestoDetailComponent,
              resolve: { estadisticasBaloncesto: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EstadisticasBaloncestoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EstadisticasBaloncestoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load estadisticasBaloncesto on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EstadisticasBaloncestoDetailComponent);

      // THEN
      expect(instance.estadisticasBaloncesto()).toEqual(expect.objectContaining({ id: 123 }));
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
