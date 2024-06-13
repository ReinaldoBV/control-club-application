import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComuna } from '../comuna.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../comuna.test-samples';

import { ComunaService } from './comuna.service';

const requireRestSample: IComuna = {
  ...sampleWithRequiredData,
};

describe('Comuna Service', () => {
  let service: ComunaService;
  let httpMock: HttpTestingController;
  let expectedResult: IComuna | IComuna[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ComunaService);
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

    it('should create a Comuna', () => {
      const comuna = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comuna).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Comuna', () => {
      const comuna = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comuna).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Comuna', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Comuna', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Comuna', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addComunaToCollectionIfMissing', () => {
      it('should add a Comuna to an empty array', () => {
        const comuna: IComuna = sampleWithRequiredData;
        expectedResult = service.addComunaToCollectionIfMissing([], comuna);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comuna);
      });

      it('should not add a Comuna to an array that contains it', () => {
        const comuna: IComuna = sampleWithRequiredData;
        const comunaCollection: IComuna[] = [
          {
            ...comuna,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addComunaToCollectionIfMissing(comunaCollection, comuna);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Comuna to an array that doesn't contain it", () => {
        const comuna: IComuna = sampleWithRequiredData;
        const comunaCollection: IComuna[] = [sampleWithPartialData];
        expectedResult = service.addComunaToCollectionIfMissing(comunaCollection, comuna);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comuna);
      });

      it('should add only unique Comuna to an array', () => {
        const comunaArray: IComuna[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const comunaCollection: IComuna[] = [sampleWithRequiredData];
        expectedResult = service.addComunaToCollectionIfMissing(comunaCollection, ...comunaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comuna: IComuna = sampleWithRequiredData;
        const comuna2: IComuna = sampleWithPartialData;
        expectedResult = service.addComunaToCollectionIfMissing([], comuna, comuna2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comuna);
        expect(expectedResult).toContain(comuna2);
      });

      it('should accept null and undefined values', () => {
        const comuna: IComuna = sampleWithRequiredData;
        expectedResult = service.addComunaToCollectionIfMissing([], null, comuna, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comuna);
      });

      it('should return initial array if no Comuna is added', () => {
        const comunaCollection: IComuna[] = [sampleWithRequiredData];
        expectedResult = service.addComunaToCollectionIfMissing(comunaCollection, undefined, null);
        expect(expectedResult).toEqual(comunaCollection);
      });
    });

    describe('compareComuna', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComuna(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComuna(entity1, entity2);
        const compareResult2 = service.compareComuna(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComuna(entity1, entity2);
        const compareResult2 = service.compareComuna(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComuna(entity1, entity2);
        const compareResult2 = service.compareComuna(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
