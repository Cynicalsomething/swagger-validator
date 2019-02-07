export class ValidateRequest {
  swaggerUrl: string;
  method: string;
  path: string;
  request: string;
  response: string;

  constructor(url: string, method: string, path: string, requestJson: string, responseJson: string) {
    this.swaggerUrl = url;
    this.method = method;
    this.path = path;
    this.request = requestJson;
    this.response = responseJson;
  }
}
