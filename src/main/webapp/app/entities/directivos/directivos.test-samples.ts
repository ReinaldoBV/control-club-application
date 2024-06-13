import dayjs from 'dayjs/esm';

import { IDirectivos, NewDirectivos } from './directivos.model';

export const sampleWithRequiredData: IDirectivos = {
  id: 10250,
  nombres: 'the physically woot',
  apellidos: 'shameful',
  cargo: 'enormously',
  telefono: 'incidentally opulent fooey',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Diego64@yahoo.com',
};

export const sampleWithPartialData: IDirectivos = {
  id: 27690,
  nombres: 'kindly now',
  apellidos: 'focus',
  cargo: 'er leek',
  telefono: 'meanwhile',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Homero40@hotmail.com',
};

export const sampleWithFullData: IDirectivos = {
  id: 26798,
  nombres: 'really woot oh',
  apellidos: 'broccoli',
  cargo: 'outside yuck',
  telefono: 'actually clown',
  fechaEleccion: dayjs('2024-06-13'),
  fechaVencimiento: dayjs('2024-06-12'),
  email: 'Claudia.LongoriaLozano98@hotmail.com',
};

export const sampleWithNewData: NewDirectivos = {
  nombres: 'energetically',
  apellidos: 'pace luggage neck',
  cargo: 'cruelly',
  telefono: 'cool upbeat whether',
  fechaEleccion: dayjs('2024-06-12'),
  fechaVencimiento: dayjs('2024-06-13'),
  email: 'Mariano_MarroquinNegron60@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
