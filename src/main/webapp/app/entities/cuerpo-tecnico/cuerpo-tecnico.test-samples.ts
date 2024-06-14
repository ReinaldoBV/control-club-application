import dayjs from 'dayjs/esm';

import { ICuerpoTecnico, NewCuerpoTecnico } from './cuerpo-tecnico.model';

export const sampleWithRequiredData: ICuerpoTecnico = {
  id: 29080,
  nombres: 'stupidity yang',
  apellidos: 'famous',
  rolTecnico: 'shoehorn fiercely',
  telefono: 'proper',
  fechaInicio: dayjs('2024-06-12'),
  email: 'Barbara.TiradoAlonzo75@hotmail.com',
};

export const sampleWithPartialData: ICuerpoTecnico = {
  id: 17148,
  nombres: 'go-kart whereas requisition',
  apellidos: 'so',
  rolTecnico: 'if spasm fireman',
  telefono: 'blah',
  fechaInicio: dayjs('2024-06-13'),
  email: 'Pablo.BrionesCasanova21@hotmail.com',
};

export const sampleWithFullData: ICuerpoTecnico = {
  id: 18274,
  nombres: 'qua',
  apellidos: 'er sympathetically rapidly',
  rolTecnico: 'regarding overlooked',
  telefono: 'indeed insure',
  fechaInicio: dayjs('2024-06-12'),
  email: 'Rodrigo_OrtaGonzalez63@gmail.com',
};

export const sampleWithNewData: NewCuerpoTecnico = {
  nombres: 'though enchant',
  apellidos: 'restfully generously',
  rolTecnico: 'radio kick',
  telefono: 'abaft lapse lest',
  fechaInicio: dayjs('2024-06-13'),
  email: 'Mayte.RinconBlanco66@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
