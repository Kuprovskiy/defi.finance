import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccountBalance } from 'app/shared/model/account-balance.model';

@Component({
  selector: 'jhi-account-balance-detail',
  templateUrl: './account-balance-detail.component.html'
})
export class AccountBalanceDetailComponent implements OnInit {
  accountBalance: IAccountBalance;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ accountBalance }) => {
      this.accountBalance = accountBalance;
    });
  }

  previousState() {
    window.history.back();
  }
}
