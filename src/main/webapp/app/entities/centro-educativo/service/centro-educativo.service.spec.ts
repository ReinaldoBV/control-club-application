import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICentroEducativo } from '../centro-educativo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../centro-educativo.test-samples';

import { CentroEducativoService } from './centro-educativo.service';

const requireRestSample: ICentroEducativo = {
  ...sampleWithRequiredData,
};

describe('CentroEducativo Service', () => {
  let service: CentroEducativoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICentroEducativo | ICentroEducativo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CentroEducativoService);
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

    it('should create a CentroEducativo', () => {
      const centroEducativo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(centroEducativo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CentroEducativo', () => {
      const centroEducativo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(centroEducativo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CentroEducativo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CentroEducativo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CentroEducativo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCentroEducativoToCollectionIfMissing', () => {
      it('should add a CentroEducativo to an empty array', () => {
        const centroEducativo: ICentroEducativo = sampleWithRequiredData;
        expectedResult = service.addCentroEducativoToCollectionIfMissing([], centroEducativo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centroEducativo);
      });

      it('should not add a CentroEducativo to an array that contains it', () => {
        const centroEducativo: ICentroEducativo = sampleWithRequiredData;
        const centroEducativoCollection: ICentroEducativo[] = [
          {
            ...centroEducativo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCentroEducativoToCollectionIfMissing(centroEducativoCollection, centroEducativo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CentroEducativo to an array that doesn't contain it", () => {
        const centroEducativo: ICentroEducativo = sampleWithRequiredData;
        const centroEducativoCollection: ICentroEducativo[] = [sampleWithPartialData];
        expectedResult = service.addCentroEducativoToCollectionIfMissing(centroEducativoCollection, centroEducativo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centroEducativo);
      });

      it('should add only unique CentroEducativo to an array', () => {
        const centroEducativoArray: ICentroEducativo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const centroEducativoCollection: ICentroEducativo[] = [sampleWithRequiredData];
        expectedResult = service.addCentroEducativoToCollectionIfMissing(centroEducativoCollection, ...centroEducativoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const centroEducativo: ICentroEducativo = sampleWithRequiredData;
        const centroEducativo2: ICentroEducativo = sampleWithPartialData;
        expectedResult = service.addCentroEducativoToCollectionIfMissing([], centroEducativo, centroEducativo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(centroEducativo);
        expect(expectedResult).toContain(centroEducativo2);
      });

      it('should accept null and undefined values', () => {
        const centroEducativo: ICentroEducativo = sampleWithRequiredData;
        expectedResult = service.addCentroEducativoToCollectionIfMissing([], null, centroEducativo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(centroEducativo);
      });

      it('should return initial array if no CentroEducativo is added', () => {
        const centroEducativoCollection: ICentroEducativo[] = [sampleWithRequiredData];
        expectedResult = service.addCentroEducativoToCollectionIfMissing(centroEducativoCollection, undefined, null);
        expect(expectedResult).toEqual(centroEducativoCollection);
      });
    });

    describe('compareCentroEducativo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCentroEducativo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCentroEducativo(entity1, entity2);
        const compareResult2 = service.compareCentroEducativo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCentroEducativo(entity1, entity2);
        const compareResult2 = service.compareCentroEducativo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCentroEducativo(entity1, entity2);
        const compareResult2 = service.compareCentroEducativo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
