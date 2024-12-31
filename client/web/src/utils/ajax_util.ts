import { AjaxManual, AjaxOptions } from "./AjaxManual";

export function ajax(options: AjaxOptions) {
  const ajaxManual = new AjaxManual(options);
  return ajaxManual.sendRequest();
}
