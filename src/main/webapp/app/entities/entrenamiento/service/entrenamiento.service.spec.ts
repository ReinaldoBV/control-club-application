import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IEntrenamiento } from '../entrenamiento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../entrenamiento.test-samples';

import { EntrenamientoService, RestEntrenamiento } from './entrenamiento.service';

const requireRestSample: RestEntrenamiento = {
  ...sampleWithRequiredData,
  fechaHora: sampleWithRequiredData.fechaHora?.format(DATE_FORMAT),
};

describe('Entrenamiento Service', () => {
  let service: EntrenamientoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEntrenamiento | IEntrenamiento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EntrenamientoService);
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

    it('should create a Entrenamiento', () => {
      const entrenamiento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(entrenamiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Entrenamiento', () => {
      const entrenamiento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(entrenamiento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Entrenamiento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Entrenamiento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Entrenamiento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEntrenamientoToCollectionIfMissing', () => {
      it('should add a Entrenamiento to an empty array', () => {
        const entrenamiento: IEntrenamiento = sampleWithRequiredData;
        expectedResult = service.addEntrenamientoToCollectionIfMissing([], entrenamiento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entrenamiento);
      });

      it('should not add a Entrenamiento to an array that contains it', () => {
        const entrenamiento: IEntrenamiento = sampleWithRequiredData;
        const entrenamientoCollection: IEntrenamiento[] = [
          {
            ...entrenamiento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEntrenamientoToCollectionIfMissing(entrenamientoCollection, entrenamiento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Entrenamiento to an array that doesn't contain it", () => {
        const entrenamiento: IEntrenamiento = sampleWithRequiredData;
        const entrenamientoCollection: IEntrenamiento[] = [sampleWithPartialData];
        expectedResult = service.addEntrenamientoToCollectionIfMissing(entrenamientoCollection, entrenamiento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entrenamiento);
      });

      it('should add only unique Entrenamiento to an array', () => {
        const entrenamientoArray: IEntrenamiento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const entrenamientoCollection: IEntrenamiento[] = [sampleWithRequiredData];
        expectedResult = service.addEntrenamientoToCollectionIfMissing(entrenamientoCollection, ...entrenamientoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const entrenamiento: IEntrenamiento = sampleWithRequiredData;
        const entrenamiento2: IEntrenamiento = sampleWithPartialData;
        expectedResult = service.addEntrenamientoToCollectionIfMissing([], entrenamiento, entrenamiento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(entrenamiento);
        expect(expectedResult).toContain(entrenamiento2);
      });

      it('should accept null and undefined values', () => {
        const entrenamiento: IEntrenamiento = sampleWithRequiredData;
        expectedResult = service.addEntrenamientoToCollectionIfMissing([], null, entrenamiento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(entrenamiento);
      });

      it('should return initial array if no Entrenamiento is added', () => {
        const entrenamientoCollection: IEntrenamiento[] = [sampleWithRequiredData];
        expectedResult = service.addEntrenamientoToCollectionIfMissing(entrenamientoCollection, undefined, null);
        expect(expectedResult).toEqual(entrenamientoCollection);
      });
    });

    describe('compareEntrenamiento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEntrenamiento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEntrenamiento(entity1, entity2);
        const compareResult2 = service.compareEntrenamiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEntrenamiento(entity1, entity2);
        const compareResult2 = service.compareEntrenamiento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEntrenamiento(entity1, entity2);
        const compareResult2 = service.compareEntrenamiento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
