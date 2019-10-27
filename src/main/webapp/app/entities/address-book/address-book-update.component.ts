import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAddressBook, AddressBook } from 'app/shared/model/address-book.model';
import { AddressBookService } from './address-book.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-address-book-update',
  templateUrl: './address-book-update.component.html'
})
export class AddressBookUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    phoneNumber: [null, [Validators.required]],
    email: [],
    fullName: [],
    invitedDate: [null, [Validators.required]],
    createdDate: [null, [Validators.required]],
    userId: [null, Validators.required],
    friendId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected addressBookService: AddressBookService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ addressBook }) => {
      this.updateForm(addressBook);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(addressBook: IAddressBook) {
    this.editForm.patchValue({
      id: addressBook.id,
      phoneNumber: addressBook.phoneNumber,
      email: addressBook.email,
      fullName: addressBook.fullName,
      invitedDate: addressBook.invitedDate != null ? addressBook.invitedDate.format(DATE_TIME_FORMAT) : null,
      createdDate: addressBook.createdDate != null ? addressBook.createdDate.format(DATE_TIME_FORMAT) : null,
      userId: addressBook.userId,
      friendId: addressBook.friendId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const addressBook = this.createFromForm();
    if (addressBook.id !== undefined) {
      this.subscribeToSaveResponse(this.addressBookService.update(addressBook));
    } else {
      this.subscribeToSaveResponse(this.addressBookService.create(addressBook));
    }
  }

  private createFromForm(): IAddressBook {
    return {
      ...new AddressBook(),
      id: this.editForm.get(['id']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      email: this.editForm.get(['email']).value,
      fullName: this.editForm.get(['fullName']).value,
      invitedDate:
        this.editForm.get(['invitedDate']).value != null ? moment(this.editForm.get(['invitedDate']).value, DATE_TIME_FORMAT) : undefined,
      createdDate:
        this.editForm.get(['createdDate']).value != null ? moment(this.editForm.get(['createdDate']).value, DATE_TIME_FORMAT) : undefined,
      userId: this.editForm.get(['userId']).value,
      friendId: this.editForm.get(['friendId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAddressBook>>) {
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }
}
