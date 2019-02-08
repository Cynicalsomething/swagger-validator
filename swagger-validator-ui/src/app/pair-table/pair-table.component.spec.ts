import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PairTableComponent } from './pair-table.component';

describe('PairTableComponent', () => {
  let component: PairTableComponent;
  let fixture: ComponentFixture<PairTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PairTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PairTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
