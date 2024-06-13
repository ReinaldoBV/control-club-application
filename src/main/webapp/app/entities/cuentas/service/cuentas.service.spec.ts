import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICuentas } from '../cuentas.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cuentas.test-samples';

import { CuentasService, RestCuentas } from './cuentas.service';

const requireRestSample: RestCuentas = {
  ...sampleWithRequiredData,
  fechaVencimiento: sampleWithRequiredData.fechaVencimiento?.format(DATE_FORMAT),
};

describe('Cuentas Service', () => {
  let service: CuentasService;
  let httpMock: HttpTestingController;
  let expectedResult: ICuentas | ICuentas[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CuentasService);
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

    it('should create a Cuentas', () => {
      const cuentas = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cuentas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cuentas', () => {
      const cuentas = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cuentas).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cuentas', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cuentas', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cuentas', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCuentasToCollectionIfMissing', () => {
      it('should add a Cuentas to an empty array', () => {
        const cuentas: ICuentas = sampleWithRequiredData;
        expectedResult = service.addCuentasToCollectionIfMissing([], cuentas);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuentas);
      });

      it('should not add a Cuentas to an array that contains it', () => {
        const cuentas: ICuentas = sampleWithRequiredData;
        const cuentasCollection: ICuentas[] = [
          {
            ...cuentas,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCuentasToCollectionIfMissing(cuentasCollection, cuentas);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cuentas to an array that doesn't contain it", () => {
        const cuentas: ICuentas = sampleWithRequiredData;
        const cuentasCollection: ICuentas[] = [sampleWithPartialData];
        expectedResult = service.addCuentasToCollectionIfMissing(cuentasCollection, cuentas);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuentas);
      });

      it('should add only unique Cuentas to an array', () => {
        const cuentasArray: ICuentas[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cuentasCollection: ICuentas[] = [sampleWithRequiredData];
        expectedResult = service.addCuentasToCollectionIfMissing(cuentasCollection, ...cuentasArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cuentas: ICuentas = sampleWithRequiredData;
        const cuentas2: ICuentas = sampleWithPartialData;
        expectedResult = service.addCuentasToCollectionIfMissing([], cuentas, cuentas2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuentas);
        expect(expectedResult).toContain(cuentas2);
      });

      it('should accept null and undefined values', () => {
        const cuentas: ICuentas = sampleWithRequiredData;
        expectedResult = service.addCuentasToCollectionIfMissing([], null, cuentas, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuentas);
      });

      it('should return initial array if no Cuentas is added', () => {
        const cuentasCollection: ICuentas[] = [sampleWithRequiredData];
        expectedResult = service.addCuentasToCollectionIfMissing(cuentasCollection, undefined, null);
        expect(expectedResult).toEqual(cuentasCollection);
      });
    });

    describe('compareCuentas', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCuentas(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCuentas(entity1, entity2);
        const compareResult2 = service.compareCuentas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCuentas(entity1, entity2);
        const compareResult2 = service.compareCuentas(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCuentas(entity1, entity2);
        const compareResult2 = service.compareCuentas(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
