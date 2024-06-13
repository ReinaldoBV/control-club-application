import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPartido } from '../partido.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../partido.test-samples';

import { PartidoService, RestPartido } from './partido.service';

const requireRestSample: RestPartido = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('Partido Service', () => {
  let service: PartidoService;
  let httpMock: HttpTestingController;
  let expectedResult: IPartido | IPartido[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PartidoService);
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

    it('should create a Partido', () => {
      const partido = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(partido).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Partido', () => {
      const partido = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(partido).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Partido', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Partido', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Partido', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPartidoToCollectionIfMissing', () => {
      it('should add a Partido to an empty array', () => {
        const partido: IPartido = sampleWithRequiredData;
        expectedResult = service.addPartidoToCollectionIfMissing([], partido);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partido);
      });

      it('should not add a Partido to an array that contains it', () => {
        const partido: IPartido = sampleWithRequiredData;
        const partidoCollection: IPartido[] = [
          {
            ...partido,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPartidoToCollectionIfMissing(partidoCollection, partido);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Partido to an array that doesn't contain it", () => {
        const partido: IPartido = sampleWithRequiredData;
        const partidoCollection: IPartido[] = [sampleWithPartialData];
        expectedResult = service.addPartidoToCollectionIfMissing(partidoCollection, partido);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partido);
      });

      it('should add only unique Partido to an array', () => {
        const partidoArray: IPartido[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const partidoCollection: IPartido[] = [sampleWithRequiredData];
        expectedResult = service.addPartidoToCollectionIfMissing(partidoCollection, ...partidoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const partido: IPartido = sampleWithRequiredData;
        const partido2: IPartido = sampleWithPartialData;
        expectedResult = service.addPartidoToCollectionIfMissing([], partido, partido2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(partido);
        expect(expectedResult).toContain(partido2);
      });

      it('should accept null and undefined values', () => {
        const partido: IPartido = sampleWithRequiredData;
        expectedResult = service.addPartidoToCollectionIfMissing([], null, partido, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(partido);
      });

      it('should return initial array if no Partido is added', () => {
        const partidoCollection: IPartido[] = [sampleWithRequiredData];
        expectedResult = service.addPartidoToCollectionIfMissing(partidoCollection, undefined, null);
        expect(expectedResult).toEqual(partidoCollection);
      });
    });

    describe('comparePartido', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePartido(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePartido(entity1, entity2);
        const compareResult2 = service.comparePartido(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePartido(entity1, entity2);
        const compareResult2 = service.comparePartido(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePartido(entity1, entity2);
        const compareResult2 = service.comparePartido(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
