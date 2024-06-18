import dayjs from 'dayjs/esm';

import { ICuerpoTecnico, NewCuerpoTecnico } from './cuerpo-tecnico.model';

export const sampleWithRequiredData: ICuerpoTecnico = {
  id: 26527,
  nombres: 'cheek',
  apellidos: 'because statistics parched',
  rolTecnico: 'turban',
  telefono: 'lending',
  fechaInicio: dayjs('2024-06-12'),
  email: 'Esperanza_VanegasMarin@yahoo.com',
};

export const sampleWithPartialData: ICuerpoTecnico = {
  id: 1487,
  nombres: 'phooey',
  apellidos: 'worried under party',
  rolTecnico: 'aboard bah embellishment',
  telefono: 'collectivisation hm ugh',
  fechaInicio: dayjs('2024-06-13'),
  email: 'MariaJose80@gmail.com',
};

export const sampleWithFullData: ICuerpoTecnico = {
  id: 1532,
  nombres: 'classification rough',
  apellidos: 'apprehensive',
  rolTecnico: 'runny enchanted',
  telefono: 'yippee',
  fechaInicio: dayjs('2024-06-13'),
  email: 'JorgeLuis.VanegasBadillo@gmail.com',
};

export const sampleWithNewData: NewCuerpoTecnico = {
  nombres: 'ew boohoo',
  apellidos: 'bubbly',
  rolTecnico: 'gosh how cuddly',
  telefono: 'oh cleverly after',
  fechaInicio: dayjs('2024-06-13'),
  email: 'Rafael_GilCarbajal@yahoo.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
