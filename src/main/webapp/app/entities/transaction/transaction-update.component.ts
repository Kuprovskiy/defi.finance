import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IAsset } from 'app/shared/model/asset.model';
import { AssetService } from 'app/entities/asset';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving: boolean;

  assets: IAsset[];

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.required]],
    createdAt: [null, [Validators.required]],
    transactionType: [null, [Validators.required]],
    txHash: [null, [Validators.required]],
    assetId: [null, Validators.required],
    userId: [null, Validators.required],
    recipientId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionService: TransactionService,
    protected assetService: AssetService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
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

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      amount: transaction.amount,
      createdAt: transaction.createdAt != null ? transaction.createdAt.format(DATE_TIME_FORMAT) : null,
      transactionType: transaction.transactionType,
      txHash: transaction.txHash,
      assetId: transaction.assetId,
      userId: transaction.userId,
      recipientId: transaction.recipientId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      amount: this.editForm.get(['amount']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      transactionType: this.editForm.get(['transactionType']).value,
      txHash: this.editForm.get(['txHash']).value,
      assetId: this.editForm.get(['assetId']).value,
      userId: this.editForm.get(['userId']).value,
      recipientId: this.editForm.get(['recipientId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
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
