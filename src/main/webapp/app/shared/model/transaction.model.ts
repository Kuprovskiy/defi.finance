import { Moment } from 'moment';

export const enum TransactionType {
  SUPPLY = 'SUPPLY',
  WITHDRAW = 'WITHDRAW',
  FAUCET = 'FAUCET',
  TRANSFER = 'TRANSFER',
  INCOME = 'INCOME'
}

export interface ITransaction {
  id?: number;
  amount?: number;
  createdAt?: Moment;
  transactionType?: TransactionType;
  txHash?: string;
  assetId?: number;
  userLogin?: string;
  userId?: number;
  recipientLogin?: string;
  recipientId?: number;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public amount?: number,
    public createdAt?: Moment,
    public transactionType?: TransactionType,
    public txHash?: string,
    public assetId?: number,
    public userLogin?: string,
    public userId?: number,
    public recipientLogin?: string,
    public recipientId?: number
  ) {}
}
