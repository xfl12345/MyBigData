<template>
  <div style="width: 100%; display: flex; flex-direction: row; justify-content: center">
    <div style="flex-grow: 0.9">
      <div class="foxyInputColor jsInsertBox" style="width: 100%">
        {{ jsInsertContent }}
      </div>
      <br />
      <br />
      <div ref="formTable" style="width: 100%">
        <div>
          requestMethod:<br />
          <label for="requestMethod">
            <input id="requestMethod" v-model="requestMethod" class="foxyInputColor" type="text" />
          </label>
        </div>
        <br />
        <div>
          requestURL:<br />
          <label for="requestURL">
            <input id="requestURL" v-model="requestURL" class="foxyInputColor" type="text" />
          </label>
        </div>
        <br />
        <div>
          requsetInput:<br />
          <textarea
            v-model="requestInput"
            class="foxyInputColor"
            type="text"
            style="height: 500px"
          ></textarea>
        </div>
        <br />
        <br />
        <button title="发送请求" class="mySubmitBtn" style="width: auto" @click="postForm">发送请求</button>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { ajax } from "../utils/ajax_util";

export default defineComponent({
  name: "AjaxTest",
  data() {
    return {
      jsInsertContent: "",
      requestURL: "",
      requestMethod: "",
      requestInput: ""
    };
  },
  methods: {
    postForm() {
      console.log("postForm exec...");
      const myself = this;
      const jsonData = myself.requestInput;
      try {
        ajax({
          url: myself.requestURL,
          type: myself.requestMethod,
          data: jsonData,
          timeout: 50000, // 5秒超时
          contentType: "application/json;charset=utf-8",
          ontimeout: (event) => {
            console.log(event);
            myself.jsInsertContent = "请求超时。";
          }
        }).then(
          (xhr: XMLHttpRequest) => {
            const recvData = xhr.responseText;
            console.log(recvData);
            // 服务器返回响应，根据响应结果，分析是否请求成功
            const testJsonStr = '{"flag":false,"StudentID":"1","CourseID":"1","score":"80"}';
            const recvDataObj = JSON.parse(recvData);
            console.log(recvDataObj.success);
            myself.jsInsertContent = JSON.stringify(recvDataObj);
          },
          (xhr: XMLHttpRequest) => {
            console.log(xhr.readyState);
            myself.jsInsertContent = "请求异常。";
          }
        );
      } catch (e) {
        console.error(e);
        myself.jsInsertContent = "请求异常。";
      }
    }
  }
});
</script>

<style scoped>
input,
textarea {
  width: 90%;
  background-color: transparent;
  border-style: solid;
  border-color: blue;
  border-radius: 15px;
  padding: 15px;
  font-size: xx-large;
  box-shadow: none;
}

.jsInsertBox {
  border-style: solid;
  word-wrap: break-word;
  text-align: center;
}

.foxyInputColor {
  color: orangered;
}

.mySubmitBtn {
  border-style: solid;
  background-color: transparent;
  color: #00b0ff;
}

.mySubmitBtn:hover {
  border-style: outset;
  background-color: #00b0ff;
  color: white;
}

.mySubmitBtn,
.mySubmitBtn:hover {
  font-family: "楷体", serif;
  font-size: 2em;
  width: 80%;
  height: auto;
  border-color: #00b0ff;
  border-radius: 25px;
  box-shadow: none;
}
</style>
