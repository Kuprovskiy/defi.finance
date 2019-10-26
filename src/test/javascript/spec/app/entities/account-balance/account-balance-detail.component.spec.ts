/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefiTestModule } from '../../../test.module';
import { AccountBalanceDetailComponent } from 'app/entities/account-balance/account-balance-detail.component';
import { AccountBalance } from 'app/shared/model/account-balance.model';

describe('Component Tests', () => {
  describe('AccountBalance Management Detail Component', () => {
    let comp: AccountBalanceDetailComponent;
    let fixture: ComponentFixture<AccountBalanceDetailComponent>;
    const route = ({ data: of({ accountBalance: new AccountBalance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DefiTestModule],
        declarations: [AccountBalanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AccountBalanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AccountBalanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.accountBalance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
