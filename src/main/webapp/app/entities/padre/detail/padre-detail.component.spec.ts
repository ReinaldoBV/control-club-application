import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PadreDetailComponent } from './padre-detail.component';

describe('Padre Management Detail Component', () => {
  let comp: PadreDetailComponent;
  let fixture: ComponentFixture<PadreDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PadreDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PadreDetailComponent,
              resolve: { padre: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PadreDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PadreDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load padre on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PadreDetailComponent);

      // THEN
      expect(instance.padre()).toEqual(expect.objectContaining({ id: 123 }));
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
