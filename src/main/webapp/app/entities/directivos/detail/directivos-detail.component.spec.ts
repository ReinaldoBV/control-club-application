import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { DirectivosDetailComponent } from './directivos-detail.component';

describe('Directivos Management Detail Component', () => {
  let comp: DirectivosDetailComponent;
  let fixture: ComponentFixture<DirectivosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DirectivosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: DirectivosDetailComponent,
              resolve: { directivos: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(DirectivosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DirectivosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load directivos on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', DirectivosDetailComponent);

      // THEN
      expect(instance.directivos()).toEqual(expect.objectContaining({ id: 123 }));
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
