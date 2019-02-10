import {Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ParseResult} from "./ParseResult";
import {Path} from "./Path";
import {ValidateRequest} from "./ValidateRequest";

@Component({
  selector: 'app-swagger',
  templateUrl: './swagger.component.html',
  styleUrls: ['./swagger.component.css']
})
@Injectable()
export class SwaggerComponent implements OnInit {

  @ViewChild('pair') pair;

  swaggerLoading: boolean = false;
  parseResult: ParseResult;
  selectedPath: Path;
  selectedMethod: string;
  queryParams: Map<string, string>;
  requestJson: string;
  responseJson: string;

  validating: boolean = false;
  validationMessages: string = '';

  constructor(private http: HttpClient) { }

  ngOnInit() {}

  parseSwagger(url: string) {
    this.swaggerLoading = true;
    this.http.post<ParseResult>('/api/parseSwagger', {url: url}).subscribe(res => {
      this.parseResult = res;
      this.swaggerLoading = false;
    },
    error => {
      console.log(error)
    });
  }

  validateSwagger(url: string) {
    this.validating = true;

    const convMap = {};
    this.queryParams.forEach((value: string, key: string) => {
      convMap[key] = value;
    });
    let request = new ValidateRequest(url, this.selectedMethod, this.selectedPath.path, this.requestJson, convMap, this.responseJson);

    this.http.post('/api/swaggerValidation', request, {responseType: 'text'}).subscribe(res => {
        this.validationMessages = res;
        this.validating = false;
      },
      error => {
        console.log(error)
      }
    )
  }

  paramChanged(params: Map<string, string>) {
    this.queryParams = params;
  }

  step1Completed() : boolean {
    return this.parseResult != null && this.parseResult.errors.length == 0;
  }

  step2Complete(): boolean {
    return this.selectedPath != null && this.selectedMethod != null;
  }

  step3Complete(): boolean {
    return this.requestJson != null || this.selectedMethod == 'GET';
  }

  step4Complete(): boolean {
    return this.responseJson != null;
  }

  isGETMethodSelected(): boolean {
    return this.selectedMethod == 'GET'
  }
}
