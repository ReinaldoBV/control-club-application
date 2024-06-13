import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EntrenamientoDetailComponent } from './entrenamiento-detail.component';

describe('Entrenamiento Management Detail Component', () => {
  let comp: EntrenamientoDetailComponent;
  let fixture: ComponentFixture<EntrenamientoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrenamientoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EntrenamientoDetailComponent,
              resolve: { entrenamiento: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EntrenamientoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EntrenamientoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load entrenamiento on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EntrenamientoDetailComponent);

      // THEN
      expect(instance.entrenamiento()).toEqual(expect.objectContaining({ id: 123 }));
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
