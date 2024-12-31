<template>
  <div>
    <div id="jsInsert" class="foxyInputColor" style="border-style: solid; word-wrap: break-word"></div>
    <div style="position: center; vertical-align: center">
      <button @click="btnClick">Click me</button>
    </div>
  </div>
</template>

<script lang="tsx">
import axios from "axios";

const postData = {
  rootTableName: "fragmentRecord"
};

// const requestObject = new JsonApiCommonRequestObject();
// // 操作是更新
// requestObject.operation = "update";
// requestObject.data = postData;

const client = axios.post("./durid/basic.json", postData, {
  timeout: 10000, // 10000，几乎无限时间，长连接等待
  headers: {
    "Content-Type": "application/json;charset=utf-8"
  }
});

export default {
  name: "TestJsonApi",
  data() {
    return {
      client
    };
  },
  methods: {
    btnClick: () => {
      const jsInsertEle = document.getElementById("jsInsert");
      client.then(
        (recvData) => {
          console.log(recvData);
          // 服务器返回响应，根据响应结果，分析是否请求成功
          const testJsonStr = '{"flag":false,"StudentID":"1","CourseID":"1","score":"80"}';
          const recvDataObj = JSON.parse(recvData.data);
          console.log(recvDataObj.success);
          jsInsertEle!.innerText = JSON.stringify(recvDataObj);
        },
        (e) => {
          console.log(e);
          jsInsertEle!.innerText = "请求异常。";
        }
      );
    }
  }
};
</script>

<style scoped>
.foxyInputColor {
  color: orangered;
}
</style>
