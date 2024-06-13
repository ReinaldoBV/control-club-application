import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CuerpoTecnicoDetailComponent } from './cuerpo-tecnico-detail.component';

describe('CuerpoTecnico Management Detail Component', () => {
  let comp: CuerpoTecnicoDetailComponent;
  let fixture: ComponentFixture<CuerpoTecnicoDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CuerpoTecnicoDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CuerpoTecnicoDetailComponent,
              resolve: { cuerpoTecnico: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CuerpoTecnicoDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CuerpoTecnicoDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load cuerpoTecnico on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CuerpoTecnicoDetailComponent);

      // THEN
      expect(instance.cuerpoTecnico()).toEqual(expect.objectContaining({ id: 123 }));
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
