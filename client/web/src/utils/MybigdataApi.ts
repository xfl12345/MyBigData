import axios from "axios";
import JsonApiData from "../po/JsonApiData";

function getStringByPrefix(prefix: string): Promise<JsonApiData> {
  return new Promise<JsonApiData>(
    (resolve: (jsonApiData: JsonApiData) => void, reject: (reason: any) => void) => {
      axios.post("./backend/v1/search/string/by-prefix/", { prefix }).then(
        (response) => {
          const responseData = response.data;
          if (responseData.code === 200) {
            resolve(responseData);
          } else {
            reject(responseData);
          }
        },
        (reason) => {
          reject(reason);
        }
      );
    }
  );
}

function getNumberByPrefix(prefix: string): Promise<JsonApiData> {
  return new Promise<JsonApiData>(
    (resolve: (jsonApiData: JsonApiData) => void, reject: (reason: any) => void) => {
      axios.post("./backend/v1/search/number/by-prefix/", { prefix }).then(
        (response) => {
          const responseData = response.data;
          if (responseData.code === 200) {
            resolve(responseData);
          } else {
            reject(responseData);
          }
        },
        (reason) => {
          reject(reason);
        }
      );
    }
  );
}

export { getStringByPrefix, getNumberByPrefix };
