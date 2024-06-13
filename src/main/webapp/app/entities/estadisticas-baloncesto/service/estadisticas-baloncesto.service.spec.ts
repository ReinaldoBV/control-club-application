import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEstadisticasBaloncesto } from '../estadisticas-baloncesto.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../estadisticas-baloncesto.test-samples';

import { EstadisticasBaloncestoService } from './estadisticas-baloncesto.service';

const requireRestSample: IEstadisticasBaloncesto = {
  ...sampleWithRequiredData,
};

describe('EstadisticasBaloncesto Service', () => {
  let service: EstadisticasBaloncestoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEstadisticasBaloncesto | IEstadisticasBaloncesto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EstadisticasBaloncestoService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EstadisticasBaloncesto', () => {
      const estadisticasBaloncesto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(estadisticasBaloncesto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EstadisticasBaloncesto', () => {
      const estadisticasBaloncesto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(estadisticasBaloncesto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EstadisticasBaloncesto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EstadisticasBaloncesto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EstadisticasBaloncesto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEstadisticasBaloncestoToCollectionIfMissing', () => {
      it('should add a EstadisticasBaloncesto to an empty array', () => {
        const estadisticasBaloncesto: IEstadisticasBaloncesto = sampleWithRequiredData;
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing([], estadisticasBaloncesto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estadisticasBaloncesto);
      });

      it('should not add a EstadisticasBaloncesto to an array that contains it', () => {
        const estadisticasBaloncesto: IEstadisticasBaloncesto = sampleWithRequiredData;
        const estadisticasBaloncestoCollection: IEstadisticasBaloncesto[] = [
          {
            ...estadisticasBaloncesto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing(estadisticasBaloncestoCollection, estadisticasBaloncesto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EstadisticasBaloncesto to an array that doesn't contain it", () => {
        const estadisticasBaloncesto: IEstadisticasBaloncesto = sampleWithRequiredData;
        const estadisticasBaloncestoCollection: IEstadisticasBaloncesto[] = [sampleWithPartialData];
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing(estadisticasBaloncestoCollection, estadisticasBaloncesto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estadisticasBaloncesto);
      });

      it('should add only unique EstadisticasBaloncesto to an array', () => {
        const estadisticasBaloncestoArray: IEstadisticasBaloncesto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const estadisticasBaloncestoCollection: IEstadisticasBaloncesto[] = [sampleWithRequiredData];
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing(
          estadisticasBaloncestoCollection,
          ...estadisticasBaloncestoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const estadisticasBaloncesto: IEstadisticasBaloncesto = sampleWithRequiredData;
        const estadisticasBaloncesto2: IEstadisticasBaloncesto = sampleWithPartialData;
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing([], estadisticasBaloncesto, estadisticasBaloncesto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estadisticasBaloncesto);
        expect(expectedResult).toContain(estadisticasBaloncesto2);
      });

      it('should accept null and undefined values', () => {
        const estadisticasBaloncesto: IEstadisticasBaloncesto = sampleWithRequiredData;
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing([], null, estadisticasBaloncesto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estadisticasBaloncesto);
      });

      it('should return initial array if no EstadisticasBaloncesto is added', () => {
        const estadisticasBaloncestoCollection: IEstadisticasBaloncesto[] = [sampleWithRequiredData];
        expectedResult = service.addEstadisticasBaloncestoToCollectionIfMissing(estadisticasBaloncestoCollection, undefined, null);
        expect(expectedResult).toEqual(estadisticasBaloncestoCollection);
      });
    });

    describe('compareEstadisticasBaloncesto', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEstadisticasBaloncesto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEstadisticasBaloncesto(entity1, entity2);
        const compareResult2 = service.compareEstadisticasBaloncesto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEstadisticasBaloncesto(entity1, entity2);
        const compareResult2 = service.compareEstadisticasBaloncesto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEstadisticasBaloncesto(entity1, entity2);
        const compareResult2 = service.compareEstadisticasBaloncesto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
