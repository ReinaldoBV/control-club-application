import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { MensajeDetailComponent } from './mensaje-detail.component';

describe('Mensaje Management Detail Component', () => {
  let comp: MensajeDetailComponent;
  let fixture: ComponentFixture<MensajeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MensajeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: MensajeDetailComponent,
              resolve: { mensaje: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(MensajeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MensajeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mensaje on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', MensajeDetailComponent);

      // THEN
      expect(instance.mensaje()).toEqual(expect.objectContaining({ id: 123 }));
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
