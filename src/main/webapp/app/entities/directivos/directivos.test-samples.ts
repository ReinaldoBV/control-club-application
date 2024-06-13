import dayjs from 'dayjs/esm';

import { IDirectivos, NewDirectivos } from './directivos.model';

export const sampleWithRequiredData: IDirectivos = {
  id: 22539,
  nombres: 'playfully',
  apellidos: 'promise',
  cargo: 'from',
  telefono: 'apropos surprisingly',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'David31@yahoo.com',
};

export const sampleWithPartialData: IDirectivos = {
  id: 5379,
  nombres: 'consequently',
  apellidos: 'which',
  cargo: 'gratefully night',
  telefono: 'till finally worriedly',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'MariaCristina.GironDelgadillo@yahoo.com',
};

export const sampleWithFullData: IDirectivos = {
  id: 13014,
  nombres: 'mockingly amongst',
  apellidos: 'heavenly',
  cargo: 'slowly sound fantastic',
  telefono: 'gazump fumble',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Andres.ArguelloSandoval@gmail.com',
};

export const sampleWithNewData: NewDirectivos = {
  nombres: 'ew',
  apellidos: 'urn bah',
  cargo: 'majority',
  telefono: 'bodge finally',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Joaquin.RoldanGranados77@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
