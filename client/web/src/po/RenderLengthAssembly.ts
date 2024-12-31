import { isDefinedAndNotNull } from "../utils/IsDefinedAndNotNull";

export class RenderLengthAssembly {
  value: number = 0;

  unit: string = "px";

  static getNewInstance(value: number = 0, unit: string = "px") {
    const obj = new RenderLengthAssembly();
    if (isDefinedAndNotNull(value)) {
      obj.value = value;
    }

    if (isDefinedAndNotNull(unit)) {
      obj.unit = unit;
    }

    return obj;
  }

  toString(): string {
    return this.value + this.unit;
  }
}
