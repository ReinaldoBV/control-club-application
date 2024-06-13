import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 8442,
  nombres: 'car forked',
  apellidos: 'signal slimy sum',
  cargo: 'brr',
  telefono: 'meanwhile italicise',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Monica.SalcidoMondragon@gmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 25812,
  nombres: 'agree although yieldingly',
  apellidos: 'rash',
  cargo: 'promotion and',
  telefono: 'ha ethical round',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'Alicia.MoralesRael98@hotmail.com',
};

export const sampleWithFullData: IAsociados = {
  id: 20895,
  nombres: 'dutiful whenever however',
  apellidos: 'fatal messy',
  cargo: 'forearm rack molder',
  telefono: 'sculpture',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Fernando_GuevaraNava7@gmail.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'impose if even',
  apellidos: 'urgently inasmuch',
  cargo: 'sword',
  telefono: 'slang whether',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Armando.VelascoVargas@hotmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
