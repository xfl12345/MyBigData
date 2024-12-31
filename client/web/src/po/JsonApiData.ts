export default class JsonApiData {
  success: boolean = false;

  version: string = "undefined";

  message: string | null = null;

  code: number = 404;

  data: object | Array<object> | null = [];
}
