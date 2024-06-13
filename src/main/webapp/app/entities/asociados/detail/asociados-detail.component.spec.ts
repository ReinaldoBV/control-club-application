import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AsociadosDetailComponent } from './asociados-detail.component';

describe('Asociados Management Detail Component', () => {
  let comp: AsociadosDetailComponent;
  let fixture: ComponentFixture<AsociadosDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AsociadosDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: AsociadosDetailComponent,
              resolve: { asociados: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AsociadosDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AsociadosDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load asociados on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AsociadosDetailComponent);

      // THEN
      expect(instance.asociados()).toEqual(expect.objectContaining({ id: 123 }));
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
