import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { FinanzasEgresoService } from '../service/finanzas-egreso.service';

import finanzasEgresoResolve from './finanzas-egreso-routing-resolve.service';

describe('FinanzasEgreso routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FinanzasEgresoService;
  let resultFinanzasEgreso: IFinanzasEgreso | null | undefined;

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
    service = TestBed.inject(FinanzasEgresoService);
    resultFinanzasEgreso = undefined;
  });

  describe('resolve', () => {
    it('should return IFinanzasEgreso returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        finanzasEgresoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFinanzasEgreso = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFinanzasEgreso).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        finanzasEgresoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFinanzasEgreso = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFinanzasEgreso).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFinanzasEgreso>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        finanzasEgresoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFinanzasEgreso = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFinanzasEgreso).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
