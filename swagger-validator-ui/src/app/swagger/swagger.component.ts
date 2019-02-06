import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ParseResult} from "./ParseResult";
import {MatOption, MatStepper} from "@angular/material";

@Component({
  selector: 'app-swagger',
  templateUrl: './swagger.component.html',
  styleUrls: ['./swagger.component.css']
})
@Injectable()
export class SwaggerComponent implements OnInit {

  swaggerLoading: boolean = false;
  parseResult: ParseResult;
  selectedPath: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {}

  parseSwagger(url: string, stepper: MatStepper) {
    this.swaggerLoading = true;
    this.http.post<ParseResult>('/api/parseSwagger', {url: url}).subscribe(res => {
      this.parseResult = res;
      this.swaggerLoading = false;
    },
    error => {
      console.log(error)
    });
  }

  setSelectedPath(pathOpt: MatOption) {
    this.selectedPath = pathOpt.value
  }

  step1Completed() : boolean {
    return this.parseResult != null && this.parseResult.errors.length == 0
  }

  step2Complete(): boolean {
    return this.selectedPath != ''
  }
}
