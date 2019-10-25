import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITrustedDevice, TrustedDevice } from 'app/shared/model/trusted-device.model';
import { TrustedDeviceService } from './trusted-device.service';
import { IUser, UserService } from 'app/core';

@Component({
  selector: 'jhi-trusted-device-update',
  templateUrl: './trusted-device-update.component.html'
})
export class TrustedDeviceUpdateComponent implements OnInit {
  isSaving: boolean;

  users: IUser[];

  editForm = this.fb.group({
    id: [],
    device: [null, [Validators.required]],
    deviceVersion: [null, [Validators.required]],
    deviceOs: [null, [Validators.required]],
    deviceDetails: [null, [Validators.required]],
    location: [],
    createdAt: [null, [Validators.required]],
    ipAddress: [null, [Validators.required]],
    trusted: [null, [Validators.required]],
    userId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected trustedDeviceService: TrustedDeviceService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ trustedDevice }) => {
      this.updateForm(trustedDevice);
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(trustedDevice: ITrustedDevice) {
    this.editForm.patchValue({
      id: trustedDevice.id,
      device: trustedDevice.device,
      deviceVersion: trustedDevice.deviceVersion,
      deviceOs: trustedDevice.deviceOs,
      deviceDetails: trustedDevice.deviceDetails,
      location: trustedDevice.location,
      createdAt: trustedDevice.createdAt != null ? trustedDevice.createdAt.format(DATE_TIME_FORMAT) : null,
      ipAddress: trustedDevice.ipAddress,
      trusted: trustedDevice.trusted,
      userId: trustedDevice.userId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const trustedDevice = this.createFromForm();
    if (trustedDevice.id !== undefined) {
      this.subscribeToSaveResponse(this.trustedDeviceService.update(trustedDevice));
    } else {
      this.subscribeToSaveResponse(this.trustedDeviceService.create(trustedDevice));
    }
  }

  private createFromForm(): ITrustedDevice {
    return {
      ...new TrustedDevice(),
      id: this.editForm.get(['id']).value,
      device: this.editForm.get(['device']).value,
      deviceVersion: this.editForm.get(['deviceVersion']).value,
      deviceOs: this.editForm.get(['deviceOs']).value,
      deviceDetails: this.editForm.get(['deviceDetails']).value,
      location: this.editForm.get(['location']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      ipAddress: this.editForm.get(['ipAddress']).value,
      trusted: this.editForm.get(['trusted']).value,
      userId: this.editForm.get(['userId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITrustedDevice>>) {
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
