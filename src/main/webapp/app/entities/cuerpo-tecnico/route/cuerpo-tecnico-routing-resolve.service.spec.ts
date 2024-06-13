import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { CuerpoTecnicoService } from '../service/cuerpo-tecnico.service';

import cuerpoTecnicoResolve from './cuerpo-tecnico-routing-resolve.service';

describe('CuerpoTecnico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: CuerpoTecnicoService;
  let resultCuerpoTecnico: ICuerpoTecnico | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(CuerpoTecnicoService);
    resultCuerpoTecnico = undefined;
  });

  describe('resolve', () => {
    it('should return ICuerpoTecnico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cuerpoTecnicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCuerpoTecnico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCuerpoTecnico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        cuerpoTecnicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCuerpoTecnico = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultCuerpoTecnico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ICuerpoTecnico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        cuerpoTecnicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultCuerpoTecnico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultCuerpoTecnico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
