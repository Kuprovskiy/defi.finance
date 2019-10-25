import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAsset } from 'app/shared/model/asset.model';

@Component({
  selector: 'jhi-asset-detail',
  templateUrl: './asset-detail.component.html'
})
export class AssetDetailComponent implements OnInit {
  asset: IAsset;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ asset }) => {
      this.asset = asset;
    });
  }

  previousState() {
    window.history.back();
  }
}
