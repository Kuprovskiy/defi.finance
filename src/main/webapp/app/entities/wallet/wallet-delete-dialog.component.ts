import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWallet } from 'app/shared/model/wallet.model';
import { WalletService } from './wallet.service';

@Component({
  selector: 'jhi-wallet-delete-dialog',
  templateUrl: './wallet-delete-dialog.component.html'
})
export class WalletDeleteDialogComponent {
  wallet: IWallet;

  constructor(protected walletService: WalletService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.walletService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'walletListModification',
        content: 'Deleted an wallet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-wallet-delete-popup',
  template: ''
})
export class WalletDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ wallet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(WalletDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.wallet = wallet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/wallet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/wallet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
