import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { BienesDetailComponent } from './bienes-detail.component';

describe('Bienes Management Detail Component', () => {
  let comp: BienesDetailComponent;
  let fixture: ComponentFixture<BienesDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BienesDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: BienesDetailComponent,
              resolve: { bienes: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(BienesDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BienesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load bienes on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', BienesDetailComponent);

      // THEN
      expect(instance.bienes()).toEqual(expect.objectContaining({ id: 123 }));
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
