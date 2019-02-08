import {Component, OnInit, ViewChild} from '@angular/core';
import {MatInput, MatTableDataSource} from "@angular/material";

@Component({
  selector: 'app-pair-table',
  templateUrl: './pair-table.component.html',
  styleUrls: ['./pair-table.component.css']
})
export class PairTableComponent implements OnInit {

  displayColumns = ['keys', 'values', 'ops'];

  @ViewChild('keyInput') keyInput;
  @ViewChild('valueInput') valueInput;

  pairs = new Map([
    ['asdf', 'fdsa'],
    ['qwer', 'rewq']
  ]);

  constructor() { }

  ngOnInit() {
  }

  addParam() {
    this.pairs.set(this.keyInput.nativeElement.value, this.valueInput.nativeElement.value);
    this.keyInput.nativeElement.value = '';
    this.valueInput.nativeElement.value = '';
  }

  removeParam(item: any) {
    this.pairs.delete(item.key)
  }

  getParams(): Map<string, string> {
    return this.pairs;
  }
}
