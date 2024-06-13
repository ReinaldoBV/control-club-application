import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAsociados } from '../asociados.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../asociados.test-samples';

import { AsociadosService, RestAsociados } from './asociados.service';

const requireRestSample: RestAsociados = {
  ...sampleWithRequiredData,
  fechaAsoc: sampleWithRequiredData.fechaAsoc?.format(DATE_FORMAT),
};

describe('Asociados Service', () => {
  let service: AsociadosService;
  let httpMock: HttpTestingController;
  let expectedResult: IAsociados | IAsociados[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(AsociadosService);
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

    it('should create a Asociados', () => {
      const asociados = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(asociados).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Asociados', () => {
      const asociados = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(asociados).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Asociados', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Asociados', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Asociados', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAsociadosToCollectionIfMissing', () => {
      it('should add a Asociados to an empty array', () => {
        const asociados: IAsociados = sampleWithRequiredData;
        expectedResult = service.addAsociadosToCollectionIfMissing([], asociados);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(asociados);
      });

      it('should not add a Asociados to an array that contains it', () => {
        const asociados: IAsociados = sampleWithRequiredData;
        const asociadosCollection: IAsociados[] = [
          {
            ...asociados,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAsociadosToCollectionIfMissing(asociadosCollection, asociados);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Asociados to an array that doesn't contain it", () => {
        const asociados: IAsociados = sampleWithRequiredData;
        const asociadosCollection: IAsociados[] = [sampleWithPartialData];
        expectedResult = service.addAsociadosToCollectionIfMissing(asociadosCollection, asociados);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(asociados);
      });

      it('should add only unique Asociados to an array', () => {
        const asociadosArray: IAsociados[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const asociadosCollection: IAsociados[] = [sampleWithRequiredData];
        expectedResult = service.addAsociadosToCollectionIfMissing(asociadosCollection, ...asociadosArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const asociados: IAsociados = sampleWithRequiredData;
        const asociados2: IAsociados = sampleWithPartialData;
        expectedResult = service.addAsociadosToCollectionIfMissing([], asociados, asociados2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(asociados);
        expect(expectedResult).toContain(asociados2);
      });

      it('should accept null and undefined values', () => {
        const asociados: IAsociados = sampleWithRequiredData;
        expectedResult = service.addAsociadosToCollectionIfMissing([], null, asociados, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(asociados);
      });

      it('should return initial array if no Asociados is added', () => {
        const asociadosCollection: IAsociados[] = [sampleWithRequiredData];
        expectedResult = service.addAsociadosToCollectionIfMissing(asociadosCollection, undefined, null);
        expect(expectedResult).toEqual(asociadosCollection);
      });
    });

    describe('compareAsociados', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAsociados(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAsociados(entity1, entity2);
        const compareResult2 = service.compareAsociados(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAsociados(entity1, entity2);
        const compareResult2 = service.compareAsociados(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAsociados(entity1, entity2);
        const compareResult2 = service.compareAsociados(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
