import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import { TorneosParticipacionesService } from '../service/torneos-participaciones.service';

import torneosParticipacionesResolve from './torneos-participaciones-routing-resolve.service';

describe('TorneosParticipaciones routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TorneosParticipacionesService;
  let resultTorneosParticipaciones: ITorneosParticipaciones | null | undefined;

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
    service = TestBed.inject(TorneosParticipacionesService);
    resultTorneosParticipaciones = undefined;
  });

  describe('resolve', () => {
    it('should return ITorneosParticipaciones returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        torneosParticipacionesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTorneosParticipaciones = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTorneosParticipaciones).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        torneosParticipacionesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTorneosParticipaciones = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTorneosParticipaciones).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITorneosParticipaciones>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        torneosParticipacionesResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTorneosParticipaciones = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTorneosParticipaciones).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
