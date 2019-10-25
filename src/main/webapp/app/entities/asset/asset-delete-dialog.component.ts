import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from './asset.service';

@Component({
  selector: 'jhi-asset-delete-dialog',
  templateUrl: './asset-delete-dialog.component.html'
})
export class AssetDeleteDialogComponent {
  asset: IAsset;

  constructor(protected assetService: AssetService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.assetService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'assetListModification',
        content: 'Deleted an asset'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-asset-delete-popup',
  template: ''
})
export class AssetDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ asset }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AssetDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.asset = asset;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/asset', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/asset', { outlets: { popup: null } }]);
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
