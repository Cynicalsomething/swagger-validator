export class ValidateRequest {
  swaggerUrl: string;
  method: string;
  path: string;
  request: string;
  queryParams: {};
  response: string;

  constructor(url: string, method: string, path: string, requestJson: string, queryParams: {}, responseJson: string) {
    this.swaggerUrl = url;
    this.method = method;
    this.path = path;
    this.request = requestJson;
    this.queryParams = queryParams;
    this.response = responseJson;
  }
}
