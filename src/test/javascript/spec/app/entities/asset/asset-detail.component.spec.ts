/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { AssetDetailComponent } from 'app/entities/asset/asset-detail.component';
import { Asset } from 'app/shared/model/asset.model';

describe('Component Tests', () => {
  describe('Asset Management Detail Component', () => {
    let comp: AssetDetailComponent;
    let fixture: ComponentFixture<AssetDetailComponent>;
    const route = ({ data: of({ asset: new Asset(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AssetDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AssetDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.asset).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
