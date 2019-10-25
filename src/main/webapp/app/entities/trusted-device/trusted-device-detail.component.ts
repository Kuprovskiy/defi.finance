import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITrustedDevice } from 'app/shared/model/trusted-device.model';

@Component({
  selector: 'jhi-trusted-device-detail',
  templateUrl: './trusted-device-detail.component.html'
})
export class TrustedDeviceDetailComponent implements OnInit {
  trustedDevice: ITrustedDevice;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ trustedDevice }) => {
      this.trustedDevice = trustedDevice;
    });
  }

  previousState() {
    window.history.back();
  }
}
