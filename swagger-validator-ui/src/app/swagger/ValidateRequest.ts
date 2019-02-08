export class ValidateRequest {
  swaggerUrl: string;
  method: string;
  path: string;
  request: string;
  queryParams: Map<string, string>;
  response: string;

  constructor(url: string, method: string, path: string, requestJson: string, queryParams: Map<string, string>, responseJson: string) {
    this.swaggerUrl = url;
    this.method = method;
    this.path = path;
    this.request = requestJson;
    this.queryParams = queryParams;
    this.response = responseJson;
  }
}
