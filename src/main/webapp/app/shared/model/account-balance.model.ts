import { Moment } from 'moment';

export const enum BalanceType {
  SUPPLY = 'SUPPLY',
  BORROW = 'BORROW',
  WALLET = 'WALLET'
}

export interface IAccountBalance {
  id?: number;
  balanceAmount?: number;
  createdAt?: Moment;
  updatedAt?: Moment;
  balanceType?: BalanceType;
  assetId?: number;
  userLogin?: string;
  userId?: number;
}

export class AccountBalance implements IAccountBalance {
  constructor(
    public id?: number,
    public balanceAmount?: number,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public balanceType?: BalanceType,
    public assetId?: number,
    public userLogin?: string,
    public userId?: number
  ) {}
}
