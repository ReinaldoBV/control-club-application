import dayjs from 'dayjs/esm';

import { IAsociados, NewAsociados } from './asociados.model';

export const sampleWithRequiredData: IAsociados = {
  id: 11083,
  nombres: 'psst consequently',
  apellidos: 'since failing',
  cargo: 'wrongly cute whenever',
  telefono: 'metallic',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Lilia.UribeFierro23@gmail.com',
};

export const sampleWithPartialData: IAsociados = {
  id: 31193,
  nombres: 'who',
  apellidos: 'phony uh-huh',
  cargo: 'catalogue phone grit',
  telefono: 'upright towards',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Sergio.VillalpandoEspinosa@hotmail.com',
};

export const sampleWithFullData: IAsociados = {
  id: 23822,
  nombres: 'spool counselling devoted',
  apellidos: 'before inconsequential',
  cargo: 'yum',
  telefono: 'dissolve mostly',
  fechaAsoc: dayjs('2024-06-13'),
  email: 'MariaLuisa_BotelloSisneros25@gmail.com',
};

export const sampleWithNewData: NewAsociados = {
  nombres: 'wassail huzzah',
  apellidos: 'pish ha',
  cargo: 'unnecessarily extraneous pattern',
  telefono: 'draft slash',
  fechaAsoc: dayjs('2024-06-12'),
  email: 'Victor_MirelesEsquibel@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
