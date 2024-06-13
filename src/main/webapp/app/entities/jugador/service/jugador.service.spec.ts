import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IJugador } from '../jugador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../jugador.test-samples';

import { JugadorService, RestJugador } from './jugador.service';

const requireRestSample: RestJugador = {
  ...sampleWithRequiredData,
  fechaNacimiento: sampleWithRequiredData.fechaNacimiento?.format(DATE_FORMAT),
};

describe('Jugador Service', () => {
  let service: JugadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IJugador | IJugador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(JugadorService);
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

    it('should create a Jugador', () => {
      const jugador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(jugador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Jugador', () => {
      const jugador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(jugador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Jugador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Jugador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Jugador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addJugadorToCollectionIfMissing', () => {
      it('should add a Jugador to an empty array', () => {
        const jugador: IJugador = sampleWithRequiredData;
        expectedResult = service.addJugadorToCollectionIfMissing([], jugador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jugador);
      });

      it('should not add a Jugador to an array that contains it', () => {
        const jugador: IJugador = sampleWithRequiredData;
        const jugadorCollection: IJugador[] = [
          {
            ...jugador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addJugadorToCollectionIfMissing(jugadorCollection, jugador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Jugador to an array that doesn't contain it", () => {
        const jugador: IJugador = sampleWithRequiredData;
        const jugadorCollection: IJugador[] = [sampleWithPartialData];
        expectedResult = service.addJugadorToCollectionIfMissing(jugadorCollection, jugador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jugador);
      });

      it('should add only unique Jugador to an array', () => {
        const jugadorArray: IJugador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const jugadorCollection: IJugador[] = [sampleWithRequiredData];
        expectedResult = service.addJugadorToCollectionIfMissing(jugadorCollection, ...jugadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const jugador: IJugador = sampleWithRequiredData;
        const jugador2: IJugador = sampleWithPartialData;
        expectedResult = service.addJugadorToCollectionIfMissing([], jugador, jugador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(jugador);
        expect(expectedResult).toContain(jugador2);
      });

      it('should accept null and undefined values', () => {
        const jugador: IJugador = sampleWithRequiredData;
        expectedResult = service.addJugadorToCollectionIfMissing([], null, jugador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(jugador);
      });

      it('should return initial array if no Jugador is added', () => {
        const jugadorCollection: IJugador[] = [sampleWithRequiredData];
        expectedResult = service.addJugadorToCollectionIfMissing(jugadorCollection, undefined, null);
        expect(expectedResult).toEqual(jugadorCollection);
      });
    });

    describe('compareJugador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareJugador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareJugador(entity1, entity2);
        const compareResult2 = service.compareJugador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareJugador(entity1, entity2);
        const compareResult2 = service.compareJugador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareJugador(entity1, entity2);
        const compareResult2 = service.compareJugador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
