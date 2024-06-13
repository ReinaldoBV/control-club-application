import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITorneosParticipaciones } from '../torneos-participaciones.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../torneos-participaciones.test-samples';

import { TorneosParticipacionesService, RestTorneosParticipaciones } from './torneos-participaciones.service';

const requireRestSample: RestTorneosParticipaciones = {
  ...sampleWithRequiredData,
  fecha: sampleWithRequiredData.fecha?.format(DATE_FORMAT),
};

describe('TorneosParticipaciones Service', () => {
  let service: TorneosParticipacionesService;
  let httpMock: HttpTestingController;
  let expectedResult: ITorneosParticipaciones | ITorneosParticipaciones[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TorneosParticipacionesService);
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

    it('should create a TorneosParticipaciones', () => {
      const torneosParticipaciones = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(torneosParticipaciones).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TorneosParticipaciones', () => {
      const torneosParticipaciones = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(torneosParticipaciones).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TorneosParticipaciones', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TorneosParticipaciones', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TorneosParticipaciones', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTorneosParticipacionesToCollectionIfMissing', () => {
      it('should add a TorneosParticipaciones to an empty array', () => {
        const torneosParticipaciones: ITorneosParticipaciones = sampleWithRequiredData;
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing([], torneosParticipaciones);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(torneosParticipaciones);
      });

      it('should not add a TorneosParticipaciones to an array that contains it', () => {
        const torneosParticipaciones: ITorneosParticipaciones = sampleWithRequiredData;
        const torneosParticipacionesCollection: ITorneosParticipaciones[] = [
          {
            ...torneosParticipaciones,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing(torneosParticipacionesCollection, torneosParticipaciones);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TorneosParticipaciones to an array that doesn't contain it", () => {
        const torneosParticipaciones: ITorneosParticipaciones = sampleWithRequiredData;
        const torneosParticipacionesCollection: ITorneosParticipaciones[] = [sampleWithPartialData];
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing(torneosParticipacionesCollection, torneosParticipaciones);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(torneosParticipaciones);
      });

      it('should add only unique TorneosParticipaciones to an array', () => {
        const torneosParticipacionesArray: ITorneosParticipaciones[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const torneosParticipacionesCollection: ITorneosParticipaciones[] = [sampleWithRequiredData];
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing(
          torneosParticipacionesCollection,
          ...torneosParticipacionesArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const torneosParticipaciones: ITorneosParticipaciones = sampleWithRequiredData;
        const torneosParticipaciones2: ITorneosParticipaciones = sampleWithPartialData;
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing([], torneosParticipaciones, torneosParticipaciones2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(torneosParticipaciones);
        expect(expectedResult).toContain(torneosParticipaciones2);
      });

      it('should accept null and undefined values', () => {
        const torneosParticipaciones: ITorneosParticipaciones = sampleWithRequiredData;
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing([], null, torneosParticipaciones, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(torneosParticipaciones);
      });

      it('should return initial array if no TorneosParticipaciones is added', () => {
        const torneosParticipacionesCollection: ITorneosParticipaciones[] = [sampleWithRequiredData];
        expectedResult = service.addTorneosParticipacionesToCollectionIfMissing(torneosParticipacionesCollection, undefined, null);
        expect(expectedResult).toEqual(torneosParticipacionesCollection);
      });
    });

    describe('compareTorneosParticipaciones', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTorneosParticipaciones(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTorneosParticipaciones(entity1, entity2);
        const compareResult2 = service.compareTorneosParticipaciones(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTorneosParticipaciones(entity1, entity2);
        const compareResult2 = service.compareTorneosParticipaciones(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTorneosParticipaciones(entity1, entity2);
        const compareResult2 = service.compareTorneosParticipaciones(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
