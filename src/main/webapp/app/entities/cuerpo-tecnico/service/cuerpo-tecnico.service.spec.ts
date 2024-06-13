import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICuerpoTecnico } from '../cuerpo-tecnico.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cuerpo-tecnico.test-samples';

import { CuerpoTecnicoService, RestCuerpoTecnico } from './cuerpo-tecnico.service';

const requireRestSample: RestCuerpoTecnico = {
  ...sampleWithRequiredData,
  fechaInicio: sampleWithRequiredData.fechaInicio?.format(DATE_FORMAT),
};

describe('CuerpoTecnico Service', () => {
  let service: CuerpoTecnicoService;
  let httpMock: HttpTestingController;
  let expectedResult: ICuerpoTecnico | ICuerpoTecnico[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CuerpoTecnicoService);
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

    it('should create a CuerpoTecnico', () => {
      const cuerpoTecnico = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cuerpoTecnico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CuerpoTecnico', () => {
      const cuerpoTecnico = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cuerpoTecnico).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CuerpoTecnico', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CuerpoTecnico', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CuerpoTecnico', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCuerpoTecnicoToCollectionIfMissing', () => {
      it('should add a CuerpoTecnico to an empty array', () => {
        const cuerpoTecnico: ICuerpoTecnico = sampleWithRequiredData;
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing([], cuerpoTecnico);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuerpoTecnico);
      });

      it('should not add a CuerpoTecnico to an array that contains it', () => {
        const cuerpoTecnico: ICuerpoTecnico = sampleWithRequiredData;
        const cuerpoTecnicoCollection: ICuerpoTecnico[] = [
          {
            ...cuerpoTecnico,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing(cuerpoTecnicoCollection, cuerpoTecnico);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CuerpoTecnico to an array that doesn't contain it", () => {
        const cuerpoTecnico: ICuerpoTecnico = sampleWithRequiredData;
        const cuerpoTecnicoCollection: ICuerpoTecnico[] = [sampleWithPartialData];
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing(cuerpoTecnicoCollection, cuerpoTecnico);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuerpoTecnico);
      });

      it('should add only unique CuerpoTecnico to an array', () => {
        const cuerpoTecnicoArray: ICuerpoTecnico[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cuerpoTecnicoCollection: ICuerpoTecnico[] = [sampleWithRequiredData];
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing(cuerpoTecnicoCollection, ...cuerpoTecnicoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cuerpoTecnico: ICuerpoTecnico = sampleWithRequiredData;
        const cuerpoTecnico2: ICuerpoTecnico = sampleWithPartialData;
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing([], cuerpoTecnico, cuerpoTecnico2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cuerpoTecnico);
        expect(expectedResult).toContain(cuerpoTecnico2);
      });

      it('should accept null and undefined values', () => {
        const cuerpoTecnico: ICuerpoTecnico = sampleWithRequiredData;
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing([], null, cuerpoTecnico, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cuerpoTecnico);
      });

      it('should return initial array if no CuerpoTecnico is added', () => {
        const cuerpoTecnicoCollection: ICuerpoTecnico[] = [sampleWithRequiredData];
        expectedResult = service.addCuerpoTecnicoToCollectionIfMissing(cuerpoTecnicoCollection, undefined, null);
        expect(expectedResult).toEqual(cuerpoTecnicoCollection);
      });
    });

    describe('compareCuerpoTecnico', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCuerpoTecnico(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCuerpoTecnico(entity1, entity2);
        const compareResult2 = service.compareCuerpoTecnico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCuerpoTecnico(entity1, entity2);
        const compareResult2 = service.compareCuerpoTecnico(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCuerpoTecnico(entity1, entity2);
        const compareResult2 = service.compareCuerpoTecnico(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
