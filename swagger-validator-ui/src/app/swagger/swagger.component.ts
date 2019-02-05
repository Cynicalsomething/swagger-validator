import {Component, Injectable, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-swagger',
  templateUrl: './swagger.component.html',
  styleUrls: ['./swagger.component.css']
})
@Injectable()
export class SwaggerComponent implements OnInit {

  showSwaggerLoad: boolean = false;

  form1: FormGroup;

  constructor(private _formBuilder: FormBuilder, private http: HttpClient) { }

  ngOnInit() {
    this.form1 = this._formBuilder.group({
      swaggerUrl: ['', Validators.required]
    })
  }

  get swaggerUrl(): any { return this.form1.get('swaggerUrl').value }

  test() {
    this.showSwaggerLoad = true;
    this.http.post('/api/parseSwagger', {url: this.swaggerUrl}, {responseType: 'json'}).subscribe(res => {
      console.log(res["openAPI"]["paths"])
    });
  }
}
