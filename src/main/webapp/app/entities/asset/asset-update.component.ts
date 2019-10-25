import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAsset, Asset } from 'app/shared/model/asset.model';
import { AssetService } from './asset.service';

@Component({
  selector: 'jhi-asset-update',
  templateUrl: './asset-update.component.html'
})
export class AssetUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    longName: [null, [Validators.required]],
    marketCap: [],
    price: [null, [Validators.required]],
    supply: [],
    isVisible: [null, [Validators.required]]
  });

  constructor(protected assetService: AssetService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ asset }) => {
      this.updateForm(asset);
    });
  }

  updateForm(asset: IAsset) {
    this.editForm.patchValue({
      id: asset.id,
      name: asset.name,
      longName: asset.longName,
      marketCap: asset.marketCap,
      price: asset.price,
      supply: asset.supply,
      isVisible: asset.isVisible
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const asset = this.createFromForm();
    if (asset.id !== undefined) {
      this.subscribeToSaveResponse(this.assetService.update(asset));
    } else {
      this.subscribeToSaveResponse(this.assetService.create(asset));
    }
  }

  private createFromForm(): IAsset {
    return {
      ...new Asset(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      longName: this.editForm.get(['longName']).value,
      marketCap: this.editForm.get(['marketCap']).value,
      price: this.editForm.get(['price']).value,
      supply: this.editForm.get(['supply']).value,
      isVisible: this.editForm.get(['isVisible']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAsset>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
