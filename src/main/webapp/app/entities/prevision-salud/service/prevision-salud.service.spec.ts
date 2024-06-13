import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPrevisionSalud } from '../prevision-salud.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prevision-salud.test-samples';

import { PrevisionSaludService } from './prevision-salud.service';

const requireRestSample: IPrevisionSalud = {
  ...sampleWithRequiredData,
};

describe('PrevisionSalud Service', () => {
  let service: PrevisionSaludService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrevisionSalud | IPrevisionSalud[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PrevisionSaludService);
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

    it('should create a PrevisionSalud', () => {
      const previsionSalud = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(previsionSalud).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrevisionSalud', () => {
      const previsionSalud = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(previsionSalud).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrevisionSalud', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrevisionSalud', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrevisionSalud', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrevisionSaludToCollectionIfMissing', () => {
      it('should add a PrevisionSalud to an empty array', () => {
        const previsionSalud: IPrevisionSalud = sampleWithRequiredData;
        expectedResult = service.addPrevisionSaludToCollectionIfMissing([], previsionSalud);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(previsionSalud);
      });

      it('should not add a PrevisionSalud to an array that contains it', () => {
        const previsionSalud: IPrevisionSalud = sampleWithRequiredData;
        const previsionSaludCollection: IPrevisionSalud[] = [
          {
            ...previsionSalud,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrevisionSaludToCollectionIfMissing(previsionSaludCollection, previsionSalud);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrevisionSalud to an array that doesn't contain it", () => {
        const previsionSalud: IPrevisionSalud = sampleWithRequiredData;
        const previsionSaludCollection: IPrevisionSalud[] = [sampleWithPartialData];
        expectedResult = service.addPrevisionSaludToCollectionIfMissing(previsionSaludCollection, previsionSalud);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(previsionSalud);
      });

      it('should add only unique PrevisionSalud to an array', () => {
        const previsionSaludArray: IPrevisionSalud[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const previsionSaludCollection: IPrevisionSalud[] = [sampleWithRequiredData];
        expectedResult = service.addPrevisionSaludToCollectionIfMissing(previsionSaludCollection, ...previsionSaludArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const previsionSalud: IPrevisionSalud = sampleWithRequiredData;
        const previsionSalud2: IPrevisionSalud = sampleWithPartialData;
        expectedResult = service.addPrevisionSaludToCollectionIfMissing([], previsionSalud, previsionSalud2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(previsionSalud);
        expect(expectedResult).toContain(previsionSalud2);
      });

      it('should accept null and undefined values', () => {
        const previsionSalud: IPrevisionSalud = sampleWithRequiredData;
        expectedResult = service.addPrevisionSaludToCollectionIfMissing([], null, previsionSalud, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(previsionSalud);
      });

      it('should return initial array if no PrevisionSalud is added', () => {
        const previsionSaludCollection: IPrevisionSalud[] = [sampleWithRequiredData];
        expectedResult = service.addPrevisionSaludToCollectionIfMissing(previsionSaludCollection, undefined, null);
        expect(expectedResult).toEqual(previsionSaludCollection);
      });
    });

    describe('comparePrevisionSalud', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrevisionSalud(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrevisionSalud(entity1, entity2);
        const compareResult2 = service.comparePrevisionSalud(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrevisionSalud(entity1, entity2);
        const compareResult2 = service.comparePrevisionSalud(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrevisionSalud(entity1, entity2);
        const compareResult2 = service.comparePrevisionSalud(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
