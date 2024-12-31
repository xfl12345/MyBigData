export function isStringNumeric(value: string): boolean {
  return /^(?:[-](?:[1-9]\d+|[1-9])(?:\.\d+|)|(?:[1-9]\d+|\d)(?:\.\d+|))$/.test(value);
}
