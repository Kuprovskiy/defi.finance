import { Moment } from 'moment';

export interface IWallet {
  id?: number;
  address?: string;
  createdAt?: Moment;
  userLogin?: string;
  userId?: number;
}

export class Wallet implements IWallet {
  constructor(public id?: number, public address?: string, public createdAt?: Moment, public userLogin?: string, public userId?: number) {}
}
