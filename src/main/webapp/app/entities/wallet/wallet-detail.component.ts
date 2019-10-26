import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWallet } from 'app/shared/model/wallet.model';

@Component({
  selector: 'jhi-wallet-detail',
  templateUrl: './wallet-detail.component.html'
})
export class WalletDetailComponent implements OnInit {
  wallet: IWallet;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wallet }) => {
      this.wallet = wallet;
    });
  }

  previousState() {
    window.history.back();
  }
}
