import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IFinanzasIngreso } from '../finanzas-ingreso.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../finanzas-ingreso.test-samples';

import { FinanzasIngresoService, RestFinanzasIngreso } from './finanzas-ingreso.service';

const requireRestSample: RestFinanzasIngreso = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('FinanzasIngreso Service', () => {
  let service: FinanzasIngresoService;
  let httpMock: HttpTestingController;
  let expectedResult: IFinanzasIngreso | IFinanzasIngreso[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(FinanzasIngresoService);
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

    it('should create a FinanzasIngreso', () => {
      const finanzasIngreso = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(finanzasIngreso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FinanzasIngreso', () => {
      const finanzasIngreso = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(finanzasIngreso).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FinanzasIngreso', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FinanzasIngreso', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FinanzasIngreso', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFinanzasIngresoToCollectionIfMissing', () => {
      it('should add a FinanzasIngreso to an empty array', () => {
        const finanzasIngreso: IFinanzasIngreso = sampleWithRequiredData;
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing([], finanzasIngreso);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(finanzasIngreso);
      });

      it('should not add a FinanzasIngreso to an array that contains it', () => {
        const finanzasIngreso: IFinanzasIngreso = sampleWithRequiredData;
        const finanzasIngresoCollection: IFinanzasIngreso[] = [
          {
            ...finanzasIngreso,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing(finanzasIngresoCollection, finanzasIngreso);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FinanzasIngreso to an array that doesn't contain it", () => {
        const finanzasIngreso: IFinanzasIngreso = sampleWithRequiredData;
        const finanzasIngresoCollection: IFinanzasIngreso[] = [sampleWithPartialData];
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing(finanzasIngresoCollection, finanzasIngreso);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(finanzasIngreso);
      });

      it('should add only unique FinanzasIngreso to an array', () => {
        const finanzasIngresoArray: IFinanzasIngreso[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const finanzasIngresoCollection: IFinanzasIngreso[] = [sampleWithRequiredData];
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing(finanzasIngresoCollection, ...finanzasIngresoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const finanzasIngreso: IFinanzasIngreso = sampleWithRequiredData;
        const finanzasIngreso2: IFinanzasIngreso = sampleWithPartialData;
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing([], finanzasIngreso, finanzasIngreso2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(finanzasIngreso);
        expect(expectedResult).toContain(finanzasIngreso2);
      });

      it('should accept null and undefined values', () => {
        const finanzasIngreso: IFinanzasIngreso = sampleWithRequiredData;
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing([], null, finanzasIngreso, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(finanzasIngreso);
      });

      it('should return initial array if no FinanzasIngreso is added', () => {
        const finanzasIngresoCollection: IFinanzasIngreso[] = [sampleWithRequiredData];
        expectedResult = service.addFinanzasIngresoToCollectionIfMissing(finanzasIngresoCollection, undefined, null);
        expect(expectedResult).toEqual(finanzasIngresoCollection);
      });
    });

    describe('compareFinanzasIngreso', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFinanzasIngreso(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFinanzasIngreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasIngreso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFinanzasIngreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasIngreso(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFinanzasIngreso(entity1, entity2);
        const compareResult2 = service.compareFinanzasIngreso(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
