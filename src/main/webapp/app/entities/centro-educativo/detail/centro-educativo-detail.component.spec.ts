import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CentroEducativoDetailComponent } from './centro-educativo-detail.component';

describe('CentroEducativo Management Detail Component', () => {
  let comp: CentroEducativoDetailComponent;
  let fixture: ComponentFixture<CentroEducativoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CentroEducativoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CentroEducativoDetailComponent,
              resolve: { centroEducativo: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CentroEducativoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CentroEducativoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load centroEducativo on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CentroEducativoDetailComponent);

      // THEN
      expect(instance.centroEducativo()).toEqual(expect.objectContaining({ id: 123 }));
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
