import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFinanzasEgreso } from '../finanzas-egreso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../finanzas-egreso.test-samples';

import { FinanzasEgresoService, RestFinanzasEgreso } from './finanzas-egreso.service';

const requireRestSample: RestFinanzasEgreso = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('FinanzasEgreso Service', () => {
  let service: FinanzasEgresoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFinanzasEgreso | IFinanzasEgreso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FinanzasEgresoService);
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

    it('should create a FinanzasEgreso', () => {
      const finanzasEgreso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(finanzasEgreso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FinanzasEgreso', () => {
      const finanzasEgreso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(finanzasEgreso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FinanzasEgreso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FinanzasEgreso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FinanzasEgreso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFinanzasEgresoToCollectionIfMissing', () => {
      it('should add a FinanzasEgreso to an empty array', () => {
        const finanzasEgreso: IFinanzasEgreso = sampleWithRequiredData;
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing([], finanzasEgreso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(finanzasEgreso);
      });

      it('should not add a FinanzasEgreso to an array that contains it', () => {
        const finanzasEgreso: IFinanzasEgreso = sampleWithRequiredData;
        const finanzasEgresoCollection: IFinanzasEgreso[] = [
          {
            ...finanzasEgreso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing(finanzasEgresoCollection, finanzasEgreso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FinanzasEgreso to an array that doesn't contain it", () => {
        const finanzasEgreso: IFinanzasEgreso = sampleWithRequiredData;
        const finanzasEgresoCollection: IFinanzasEgreso[] = [sampleWithPartialData];
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing(finanzasEgresoCollection, finanzasEgreso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(finanzasEgreso);
      });

      it('should add only unique FinanzasEgreso to an array', () => {
        const finanzasEgresoArray: IFinanzasEgreso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const finanzasEgresoCollection: IFinanzasEgreso[] = [sampleWithRequiredData];
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing(finanzasEgresoCollection, ...finanzasEgresoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const finanzasEgreso: IFinanzasEgreso = sampleWithRequiredData;
        const finanzasEgreso2: IFinanzasEgreso = sampleWithPartialData;
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing([], finanzasEgreso, finanzasEgreso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(finanzasEgreso);
        expect(expectedResult).toContain(finanzasEgreso2);
      });

      it('should accept null and undefined values', () => {
        const finanzasEgreso: IFinanzasEgreso = sampleWithRequiredData;
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing([], null, finanzasEgreso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(finanzasEgreso);
      });

      it('should return initial array if no FinanzasEgreso is added', () => {
        const finanzasEgresoCollection: IFinanzasEgreso[] = [sampleWithRequiredData];
        expectedResult = service.addFinanzasEgresoToCollectionIfMissing(finanzasEgresoCollection, undefined, null);
        expect(expectedResult).toEqual(finanzasEgresoCollection);
      });
    });

    describe('compareFinanzasEgreso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFinanzasEgreso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFinanzasEgreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasEgreso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFinanzasEgreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasEgreso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFinanzasEgreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasEgreso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
