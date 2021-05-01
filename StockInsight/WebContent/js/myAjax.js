/**
 * 
 */
//크로스 브라우징 처리
var getXhr = function() {
   var xmlhttp = null;
   if (window.XMLHttpRequest) {
      // code for modern browsers
      xmlhttp = new XMLHttpRequest();
   } else {
      // code for old IE browsers
      xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
   }
   return xmlhttp;
}

var myAjax = function(requestUri, queryStr, callbackFn) {
   var xhr = getXhr();
   xhr.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
         // console.log(this.responseText.trim());
         this.callbackFn = callbackFn;
         this.callbackFn();
      }
   };
   xhr.open("POST", requestUri, true);
   xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   console.log(queryStr);
   xhr.send(queryStr);
   return xhr;
};