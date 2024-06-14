import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../jugador.test-samples';

import { JugadorFormService } from './jugador-form.service';

describe('Jugador Form Service', () => {
  let service: JugadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JugadorFormService);
  });

  describe('Service methods', () => {
    describe('createJugadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createJugadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nroIdentificacion: expect.any(Object),
            tipoIdentificacion: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            nacionalidad: expect.any(Object),
            edad: expect.any(Object),
            fechaNacimiento: expect.any(Object),
            numeroCamisa: expect.any(Object),
            contactoEmergencia: expect.any(Object),
            calleAvenidaDireccion: expect.any(Object),
            numeroDireccion: expect.any(Object),
            numeroPersonal: expect.any(Object),
            imagenJugador: expect.any(Object),
            documentoIdentificacion: expect.any(Object),
            centroSalud: expect.any(Object),
            previsionSalud: expect.any(Object),
            comuna: expect.any(Object),
            centroEducativo: expect.any(Object),
            categorias: expect.any(Object),
          }),
        );
      });

      it('passing IJugador should create a new form with FormGroup', () => {
        const formGroup = service.createJugadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nroIdentificacion: expect.any(Object),
            tipoIdentificacion: expect.any(Object),
            nombres: expect.any(Object),
            apellidos: expect.any(Object),
            nacionalidad: expect.any(Object),
            edad: expect.any(Object),
            fechaNacimiento: expect.any(Object),
            numeroCamisa: expect.any(Object),
            contactoEmergencia: expect.any(Object),
            calleAvenidaDireccion: expect.any(Object),
            numeroDireccion: expect.any(Object),
            numeroPersonal: expect.any(Object),
            imagenJugador: expect.any(Object),
            documentoIdentificacion: expect.any(Object),
            centroSalud: expect.any(Object),
            previsionSalud: expect.any(Object),
            comuna: expect.any(Object),
            centroEducativo: expect.any(Object),
            categorias: expect.any(Object),
          }),
        );
      });
    });

    describe('getJugador', () => {
      it('should return NewJugador for default Jugador initial value', () => {
        const formGroup = service.createJugadorFormGroup(sampleWithNewData);

        const jugador = service.getJugador(formGroup) as any;

        expect(jugador).toMatchObject(sampleWithNewData);
      });

      it('should return NewJugador for empty Jugador initial value', () => {
        const formGroup = service.createJugadorFormGroup();

        const jugador = service.getJugador(formGroup) as any;

        expect(jugador).toMatchObject({});
      });

      it('should return IJugador', () => {
        const formGroup = service.createJugadorFormGroup(sampleWithRequiredData);

        const jugador = service.getJugador(formGroup) as any;

        expect(jugador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IJugador should not enable id FormControl', () => {
        const formGroup = service.createJugadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewJugador should disable id FormControl', () => {
        const formGroup = service.createJugadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
