import { IPadre, NewPadre } from './padre.model';

export const sampleWithRequiredData: IPadre = {
  id: 18754,
  nombres: 'meh biscuit',
  apellidos: 'brr hmph',
  relacion: 'beautifully uh-huh',
  telefono: 'which quaint spur',
  asociado: false,
  email: 'Jesus1@gmail.com',
};

export const sampleWithPartialData: IPadre = {
  id: 48,
  nombres: 'though',
  apellidos: 'tempting rambler guarantee',
  relacion: 'ghost until',
  telefono: 'bartender tall blister',
  asociado: true,
  email: 'Ana33@gmail.com',
};

export const sampleWithFullData: IPadre = {
  id: 12804,
  nombres: 'consequently',
  apellidos: 'where regionalism',
  relacion: 'excellent self-reliant',
  telefono: 'forestall lest archaeology',
  asociado: true,
  email: 'Fernando32@gmail.com',
};

export const sampleWithNewData: NewPadre = {
  nombres: 'till pfft round',
  apellidos: 'promptly yum after',
  relacion: 'yowza boohoo sans',
  telefono: 'nerve purple victoriously',
  asociado: true,
  email: 'MariadelCarmen0@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
