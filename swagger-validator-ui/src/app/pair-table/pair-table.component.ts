import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-pair-table',
  templateUrl: './pair-table.component.html',
  styleUrls: ['./pair-table.component.css']
})
export class PairTableComponent implements OnInit {

  displayColumns = ['keys', 'values', 'ops'];

  @ViewChild('keyInput') keyInput;
  @ViewChild('valueInput') valueInput;

  pairs = new Map<string, string>([]);

  @Output() paramEvent = new EventEmitter<Map<string, string>>();

  constructor() { }

  ngOnInit() {
    this.paramEvent.emit(this.pairs);
  }

  addParam() {
    this.pairs.set(this.keyInput.nativeElement.value, this.valueInput.nativeElement.value);
    this.keyInput.nativeElement.value = '';
    this.valueInput.nativeElement.value = '';
    this.paramEvent.emit(this.pairs);
  }

  removeParam(item: any) {
    this.pairs.delete(item.key)
    this.paramEvent.emit(this.pairs);
  }
}
