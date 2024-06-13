import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ComunaDetailComponent } from './comuna-detail.component';

describe('Comuna Management Detail Component', () => {
  let comp: ComunaDetailComponent;
  let fixture: ComponentFixture<ComunaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ComunaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: ComunaDetailComponent,
              resolve: { comuna: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ComunaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ComunaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load comuna on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ComunaDetailComponent);

      // THEN
      expect(instance.comuna()).toEqual(expect.objectContaining({ id: 123 }));
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
