import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IDirectivos } from '../directivos.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../directivos.test-samples';

import { DirectivosService, RestDirectivos } from './directivos.service';

const requireRestSample: RestDirectivos = {
  ...sampleWithRequiredData,
  fechaEleccion: sampleWithRequiredData.fechaEleccion?.format(DATE_FORMAT),
  fechaVencimiento: sampleWithRequiredData.fechaVencimiento?.format(DATE_FORMAT),
};

describe('Directivos Service', () => {
  let service: DirectivosService;
  let httpMock: HttpTestingController;
  let expectedResult: IDirectivos | IDirectivos[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DirectivosService);
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

    it('should create a Directivos', () => {
      const directivos = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(directivos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Directivos', () => {
      const directivos = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(directivos).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Directivos', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Directivos', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Directivos', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDirectivosToCollectionIfMissing', () => {
      it('should add a Directivos to an empty array', () => {
        const directivos: IDirectivos = sampleWithRequiredData;
        expectedResult = service.addDirectivosToCollectionIfMissing([], directivos);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(directivos);
      });

      it('should not add a Directivos to an array that contains it', () => {
        const directivos: IDirectivos = sampleWithRequiredData;
        const directivosCollection: IDirectivos[] = [
          {
            ...directivos,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDirectivosToCollectionIfMissing(directivosCollection, directivos);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Directivos to an array that doesn't contain it", () => {
        const directivos: IDirectivos = sampleWithRequiredData;
        const directivosCollection: IDirectivos[] = [sampleWithPartialData];
        expectedResult = service.addDirectivosToCollectionIfMissing(directivosCollection, directivos);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(directivos);
      });

      it('should add only unique Directivos to an array', () => {
        const directivosArray: IDirectivos[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const directivosCollection: IDirectivos[] = [sampleWithRequiredData];
        expectedResult = service.addDirectivosToCollectionIfMissing(directivosCollection, ...directivosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const directivos: IDirectivos = sampleWithRequiredData;
        const directivos2: IDirectivos = sampleWithPartialData;
        expectedResult = service.addDirectivosToCollectionIfMissing([], directivos, directivos2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(directivos);
        expect(expectedResult).toContain(directivos2);
      });

      it('should accept null and undefined values', () => {
        const directivos: IDirectivos = sampleWithRequiredData;
        expectedResult = service.addDirectivosToCollectionIfMissing([], null, directivos, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(directivos);
      });

      it('should return initial array if no Directivos is added', () => {
        const directivosCollection: IDirectivos[] = [sampleWithRequiredData];
        expectedResult = service.addDirectivosToCollectionIfMissing(directivosCollection, undefined, null);
        expect(expectedResult).toEqual(directivosCollection);
      });
    });

    describe('compareDirectivos', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDirectivos(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDirectivos(entity1, entity2);
        const compareResult2 = service.compareDirectivos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDirectivos(entity1, entity2);
        const compareResult2 = service.compareDirectivos(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDirectivos(entity1, entity2);
        const compareResult2 = service.compareDirectivos(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
