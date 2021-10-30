export interface IUsuario {
  id?: number;
  nombre?: string | null;
  apellido?: string | null;
  email?: string | null;
  clave?: string | null;
  tipo?: boolean | null;
}

export const defaultValue: Readonly<IUsuario> = {
  tipo: false,
};
