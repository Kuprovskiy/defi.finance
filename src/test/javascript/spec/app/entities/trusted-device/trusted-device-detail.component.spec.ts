/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { TrustedDeviceDetailComponent } from 'app/entities/trusted-device/trusted-device-detail.component';
import { TrustedDevice } from 'app/shared/model/trusted-device.model';

describe('Component Tests', () => {
  describe('TrustedDevice Management Detail Component', () => {
    let comp: TrustedDeviceDetailComponent;
    let fixture: ComponentFixture<TrustedDeviceDetailComponent>;
    const route = ({ data: of({ trustedDevice: new TrustedDevice(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [TrustedDeviceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrustedDeviceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrustedDeviceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trustedDevice).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
