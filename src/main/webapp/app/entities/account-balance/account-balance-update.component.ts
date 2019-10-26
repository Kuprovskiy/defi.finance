import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAccountBalance, AccountBalance } from 'app/shared/model/account-balance.model';
import { AccountBalanceService } from './account-balance.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-account-balance-update',
  templateUrl: './account-balance-update.component.html'
})
export class AccountBalanceUpdateComponent implements OnInit {
  isSaving: boolean;

  assets: IAsset[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    balanceAmount: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    updatedAt: [null, [Validators.required]],
    balanceType: [null, [Validators.required]],
    assetId: [null, Validators.required],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected accountBalanceService: AccountBalanceService,
    protected assetService: AssetService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountBalance }) => {
      this.updateForm(accountBalance);
    });
    this.assetService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAsset[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAsset[]>) => response.body)
      )
      .subscribe((res: IAsset[]) => (this.assets = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(accountBalance: IAccountBalance) {
    this.editForm.patchValue({
      id: accountBalance.id,
      balanceAmount: accountBalance.balanceAmount,
      createdAt: accountBalance.createdAt != null ? accountBalance.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: accountBalance.updatedAt != null ? accountBalance.updatedAt.format(DATE_TIME_FORMAT) : null,
      balanceType: accountBalance.balanceType,
      assetId: accountBalance.assetId,
      userId: accountBalance.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountBalance = this.createFromForm();
    if (accountBalance.id !== undefined) {
      this.subscribeToSaveResponse(this.accountBalanceService.update(accountBalance));
    } else {
      this.subscribeToSaveResponse(this.accountBalanceService.create(accountBalance));
    }
  }

  private createFromForm(): IAccountBalance {
    return {
      ...new AccountBalance(),
      id: this.editForm.get(['id']).value,
      balanceAmount: this.editForm.get(['balanceAmount']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      balanceType: this.editForm.get(['balanceType']).value,
      assetId: this.editForm.get(['assetId']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountBalance>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAssetById(index: number, item: IAsset) {
    return item.id;
  }

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
